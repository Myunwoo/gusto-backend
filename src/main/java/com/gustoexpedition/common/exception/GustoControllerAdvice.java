package com.gustoexpedition.common.exception;

import com.gustoexpedition.common.util.MessageUtil;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

/**
 * packageName    : com.gustoexpedition.common.exception
 * fileName       : GustoControllerAdvice
 * author         : fddsg
 * date           : 2026-01-15
 * description    :
 */
@ControllerAdvice
@RequiredArgsConstructor
public class GustoControllerAdvice {
    private final MessageUtil messageUtil;


    /**
     * methodName : handleNoSuchMessageException
     * author : IM HYUN WOO
     * description : 존재하지 않는 에러 코드 사용 시 발생 > messages.properties 확인 필요
     *
     * @param ex
     * @return response entity
     */
    @ExceptionHandler(NoSuchMessageException.class)
    public ResponseEntity<String> handleNoSuchMessageException(NoSuchMessageException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("[MSG0000] 메시지 코드를 찾을 수 없습니다.");
    }

    /**
     * methodName : handleCmcException
     * author : IM HYUN WOO
     * description : gusto exception 발생 시 처리
     *
     * @param e
     * @return response entity
     */
    @ExceptionHandler(GustoException.class)
    public ResponseEntity<ErrorResponse> handleGustoException(GustoException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage(), messageUtil.getFormattedMessage(e.getMessage())));
    }

    /**
     * methodName : handleEtcException
     * author : IM HYUN WOO
     * description : 기타 exception 발생 시 처리
     *
     * @param e
     * @return response entity
     */
    @ExceptionHandler({IllegalArgumentException.class, ConstraintViolationException.class, MissingServletRequestParameterException.class, MissingServletRequestPartException.class})
    public ResponseEntity<ErrorResponse> handleEtcException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("COM001", messageUtil.getFormattedMessage("COM001")));
    }

    /**
     * methodName : handleMethodArgumentNotValidException
     * author : IM HYUN WOO
     * description : column nullabe 규칙 위반, 필수 파라미터 규칙 위반, 멀티 파트 요청 규칙 위반
     *
     * @param e
     * @return response entity
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("COM002", messageUtil.getFormattedMessage("COM002")));
    }

    /**
     * methodName : handleException
     * author : IM HYUN WOO
     * description : Exception 발생시 공통 메세지 처리
     *
     * @param e
     * @return response entity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("COM001", messageUtil.getFormattedMessage("COM001")));
    }
}
