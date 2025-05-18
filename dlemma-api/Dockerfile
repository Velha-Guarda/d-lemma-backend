FROM maven:3.9.7-amazoncorretto-17 AS build

COPY src/main /app/src
COPY pom.xml /app

WORKDIR /app

RUN mvn dependency:go-offline -B

RUN mvn clean package -DskipTests

FROM amazoncorretto:17-alpine-jdk

COPY --from=build /app/target/*.jar /app/app.jar

ENV PORT=10000
EXPOSE $PORT

WORKDIR /app

ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=${PORT}"]