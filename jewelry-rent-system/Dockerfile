# Windows only: convert line breaks to unix style
FROM alpine AS converter
RUN apk add dos2unix
WORKDIR /app
COPY mvnw mvnw
RUN dos2unix ./mvnw

FROM openjdk:8-jdk-alpine AS builder
WORKDIR /app
COPY . .
RUN mv src/main/resources/application.docker.properties src/main/resources/application.properties
COPY --from=converter /app/mvnw mvnw
RUN mvnw -Dmaven.test.skip=true clean package

FROM openjdk:8-jre-alpine
EXPOSE 8080
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "./app.jar"]