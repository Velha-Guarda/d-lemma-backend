FROM maven:3.9-amazoncorretto-21 AS build

COPY src/main /app/src
COPY pom.xml /app

WORKDIR /app

RUN mvn clean install

FROM amazoncorretto:21

COPY --from=build /app/target/dlemma-api-0.0.1-SNAPSHOT.jar /app/app.jar

ENV PORT=10000
EXPOSE $PORT

WORKDIR /app

EXPOSE 8080

CMD ["java", "-jar", "app.jar", "--server.port=${PORT}"]