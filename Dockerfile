FROM openjdk:17-jdk

# JAR 파일 메인 디렉토리에 복사
COPY build/libs/*.jar app.jar

# 시스템 진입점 정의
ENTRYPOINT ["java","-jar","app.jar"]