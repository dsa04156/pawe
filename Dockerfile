# 베이스 이미지로 Java 17 JDK 사용
FROM eclipse-temurin:21-jdk

# 작업 디렉토리 설정
WORKDIR /app

# 빌드된 jar 파일 복사
COPY /build/libs/*.jar app.jar

COPY wasm/pawe_wasm.wasm /app/wasm/pawe_wasm.wasm

# 컨테이너 시작 시 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
