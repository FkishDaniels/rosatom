
# Используем официальный образ OpenJDK в качестве базового
FROM openjdk:17-jdk-slim AS build

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

COPY src src

RUN chmod +x gradlew

RUN ./gradlew clean build



FROM openjdk:17-jdk-slim
EXPOSE 8082
WORKDIR /opt/app
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar", "app.jar"]