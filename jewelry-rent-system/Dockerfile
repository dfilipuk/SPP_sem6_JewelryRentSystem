FROM openjdk:8-jdk-alpine

WORKDIR /app

COPY . /app
COPY ./src/main/resources/application.docker.properties /app/src/main/resources/application.properties

RUN ./mvnw -Dmaven.test.skip=true clean package

ENTRYPOINT ["java","-jar","/app/target/jewelry-rent-system-0.0.1-SNAPSHOT.jar"]