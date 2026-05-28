#SpringBoot는 java를 통해서 실행한다
FROM eclipse-temurin:21-jdk-alpine

# jar 파일을 복사하고 실행할 위치
WORKDIR /app

#jar(앱) 파일이 실행 후 도커 내부 포트
EXPOSE 8080

#외부에 있는 jar 파일을 이미지 내부로 복사, ADD는 압축을 자동으로 풀어줌
COPY target/*.jar app.jar

# 이미지를 실행할 명령어
ENTRYPOINT [ "java","-jar","app.jar" ]