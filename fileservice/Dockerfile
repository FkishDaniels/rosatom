
FROM openjdk:17-jdk-slim
EXPOSE 8082
WORKDIR /opt/app
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar", "app.jar"]