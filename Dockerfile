FROM openjdk:21-slim

# jar파일 복사
COPY build/libs/appling-1.0.jar appling.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","appling.jar"]