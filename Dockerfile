# syntax=docker/dockerfile:1

# ============================================
# Build Stage: Gradle을 사용하여 JAR 빌드
# ============================================
FROM gradle:8.14.3-jdk17 AS build
WORKDIR /app

# Gradle 캐시 최적화: 의존성 파일만 먼저 복사하여 캐시 활용
COPY build.gradle settings.gradle* ./
COPY gradle/ gradle/
RUN gradle dependencies --no-daemon || true

# 소스 코드 복사 및 빌드
COPY . .
RUN gradle clean bootJar -x test --no-daemon

# ============================================
# Runtime Stage: JRE만 사용하여 경량화
# ============================================
FROM eclipse-temurin:17-jre-jammy

# 보안을 위한 non-root user 생성
RUN groupadd -r spring && useradd -r -g spring spring

WORKDIR /app

# 빌드된 JAR 파일 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 파일 소유권 변경
RUN chown spring:spring app.jar

# Non-root user로 전환
USER spring

# 포트 노출
EXPOSE 8080

# JVM 최적화 옵션 추가
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:+UseG1GC"

# 애플리케이션 실행
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]

