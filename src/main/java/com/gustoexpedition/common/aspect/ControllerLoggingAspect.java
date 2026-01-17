package com.gustoexpedition.common.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * packageName : com.gustoexpedition.common.aspect
 * fileName : ControllerLoggingAspect
 * author : fddsg
 * date : 2026-01-16
 * description : RestController의 모든 요청/응답을 자동으로 로깅하는 AOP
 * 
 * 로깅 내용:
 * - 요청: HTTP Method, URI, 파라미터 (Query, Path, Body)
 * - 응답: HTTP Status, 응답 본문
 * - 에러: 예외 정보 (GustoControllerAdvice에서 처리된 에러 포함)
 * 
 * Order: 0 (가장 먼저 실행되어 모든 요청을 캡처)
 */
@Slf4j
@Aspect
@Component
@Order(0)
public class ControllerLoggingAspect {

  private final ObjectMapper objectMapper;

  public ControllerLoggingAspect() {
    this.objectMapper = new ObjectMapper();
    this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    this.objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
  }

  @Around("@within(restController)")
  public Object logController(ProceedingJoinPoint joinPoint, RestController restController) throws Throwable {
    HttpServletRequest request = getHttpServletRequest();
    if (request == null) {
      return joinPoint.proceed();
    }

    long startTime = System.currentTimeMillis();
    String method = request.getMethod();
    String uri = request.getRequestURI();
    String queryString = request.getQueryString();
    String fullUri = queryString != null ? uri + "?" + queryString : uri;

    // 요청 로깅
    Map<String, Object> requestLog = new HashMap<>();
    requestLog.put("method", method);
    requestLog.put("uri", fullUri);
    requestLog.put("controller", joinPoint.getTarget().getClass().getSimpleName());
    requestLog.put("handler", joinPoint.getSignature().getName());

    // 파라미터 로깅
    Map<String, Object> params = extractParameters(joinPoint, request);
    if (!params.isEmpty()) {
      requestLog.put("parameters", params);
    }

    log.info("=== API Request === {}", toJsonString(requestLog));

    Object result = null;
    Exception exception = null;

    try {
      result = joinPoint.proceed();
      return result;
    } catch (Exception e) {
      exception = e;
      throw e;
    } finally {
      long duration = System.currentTimeMillis() - startTime;

      // 응답 또는 에러 로깅
      Map<String, Object> responseLog = new HashMap<>();
      responseLog.put("method", method);
      responseLog.put("uri", fullUri);
      responseLog.put("duration", duration + "ms");

      if (exception != null) {
        // 에러 발생 시
        responseLog.put("status", "ERROR");
        responseLog.put("exception", exception.getClass().getSimpleName());
        responseLog.put("exceptionMessage", exception.getMessage());

        // GustoException인 경우 에러 코드도 포함
        if (exception instanceof com.gustoexpedition.common.exception.GustoException) {
          responseLog.put("errorCode", exception.getMessage());
        }

        log.error("=== API Error === {} | Exception: {}", toJsonString(responseLog), exception.getClass().getName(),
            exception);
      } else {
        // 정상 응답
        if (result instanceof ResponseEntity) {
          ResponseEntity<?> responseEntity = (ResponseEntity<?>) result;
          responseLog.put("status", responseEntity.getStatusCode().value());
          responseLog.put("statusText", responseEntity.getStatusCode().toString());

          // 응답 본문 로깅 (너무 큰 경우 제한)
          Object body = responseEntity.getBody();
          if (body != null) {
            String bodyStr = toJsonString(body);
            if (bodyStr.length() > 1000) {
              responseLog.put("responseBody", bodyStr.substring(0, 1000) + "... (truncated)");
            } else {
              responseLog.put("responseBody", bodyStr);
            }
          }
        } else {
          responseLog.put("status", "SUCCESS");
          String resultStr = toJsonString(result);
          if (resultStr.length() > 1000) {
            responseLog.put("responseBody", resultStr.substring(0, 1000) + "... (truncated)");
          } else {
            responseLog.put("responseBody", resultStr);
          }
        }

        log.info("=== API Response === {}", toJsonString(responseLog));
      }
    }
  }

  /**
   * 파라미터 추출 (Query, Path, Body)
   */
  private Map<String, Object> extractParameters(ProceedingJoinPoint joinPoint, HttpServletRequest request) {
    Map<String, Object> params = new HashMap<>();

    // Query Parameters
    Map<String, String[]> queryParams = request.getParameterMap();
    if (!queryParams.isEmpty()) {
      Map<String, Object> queryParamsMap = new HashMap<>();
      queryParams.forEach((key, values) -> {
        if (values.length == 1) {
          queryParamsMap.put(key, values[0]);
        } else {
          queryParamsMap.put(key, Arrays.asList(values));
        }
      });
      params.put("query", queryParamsMap);
    }

    // Method Arguments (Body, Path Variables 등)
    Object[] args = joinPoint.getArgs();
    if (args.length > 0) {
      Map<String, Object> argsMap = new HashMap<>();
      String[] paramNames = getParameterNames(joinPoint);

      for (int i = 0; i < args.length; i++) {
        Object arg = args[i];
        if (arg != null) {
          String paramName = i < paramNames.length ? paramNames[i] : "arg" + i;

          // HttpServletRequest, HttpServletResponse 등은 제외
          if (isExcludedType(arg)) {
            continue;
          }

          // 큰 객체는 요약만
          String argStr = toJsonString(arg);
          if (argStr.length() > 500) {
            argsMap.put(paramName, argStr.substring(0, 500) + "... (truncated)");
          } else {
            argsMap.put(paramName, arg);
          }
        }
      }

      if (!argsMap.isEmpty()) {
        params.put("body", argsMap);
      }
    }

    return params;
  }

  /**
   * 제외할 타입 확인
   */
  private boolean isExcludedType(Object arg) {
    String className = arg.getClass().getName();
    return className.startsWith("jakarta.servlet.") ||
        className.startsWith("org.springframework.web.") ||
        className.startsWith("org.springframework.validation.");
  }

  /**
   * 파라미터 이름 추출 (간단한 방법)
   */
  private String[] getParameterNames(ProceedingJoinPoint joinPoint) {
    try {
      // Spring AOP를 사용하는 경우 파라미터 이름을 직접 가져올 수 없으므로
      // 간단하게 타입 기반으로 이름 생성
      return Arrays.stream(joinPoint.getArgs())
          .map(arg -> arg != null ? arg.getClass().getSimpleName() : "null")
          .collect(Collectors.toList())
          .toArray(new String[0]);
    } catch (Exception e) {
      return new String[0];
    }
  }

  /**
   * HttpServletRequest 가져오기
   */
  private HttpServletRequest getHttpServletRequest() {
    try {
      ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
      return attributes != null ? attributes.getRequest() : null;
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * 객체를 JSON 문자열로 변환
   */
  private String toJsonString(Object obj) {
    try {
      if (obj == null) {
        return "null";
      }
      if (obj instanceof String) {
        return (String) obj;
      }
      return objectMapper.writeValueAsString(obj);
    } catch (Exception e) {
      return obj.toString();
    }
  }
}
