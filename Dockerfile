# 기본 이미지로 openjdk:17-jdk-slim 사용
FROM openjdk:17-jdk-slim

COPY build/libs/community-service-0.0.1-SNAPSHOT.jar community-service.jar
ENTRYPOINT ["java","-jar","community-service.jar"]
