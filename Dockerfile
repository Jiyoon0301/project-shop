# 기본 이미지로 openjdk:17-jdk-slim 사용
FROM openjdk:17-jdk-slim

# 작업 디렉토리 생성
WORKDIR /app

# build/libs 폴더에서 JAR 파일을 /app 폴더로 복사
COPY build/libs/shop1-0.0.1-SNAPSHOT.jar /app/shop1.jar

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app/shop1.jar"]
