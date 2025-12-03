# Stage 1: Build
FROM eclipse-temurin:17-jdk-jammy AS builder
WORKDIR /app

# 1. Gradle 파일만 먼저 복사 (의존성 캐싱용)
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# 2. 의존성 먼저 다운로드
RUN chmod +x ./gradlew
RUN ./gradlew dependencies --no-daemon

# 3. 소스 복사 후 빌드 (소스만 바뀌면 여기서부터만 재실행)
COPY src src
RUN ./gradlew clean build -x test --no-daemon

# Stage 2: Run
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=builder /app/build/libs/FinNAI-WAS-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]