FROM maven:3.9-amazoncorretto-21 AS build

COPY src/main /app/src
COPY pom.xml /app

WORKDIR /app

RUN mvn dependency:go-offline -B

RUN mvn clean package -DskipTests && \
    ls -la target/ && \
    jar tf target/dlemma-api-*.jar | grep -E "MANIFEST.MF|DLemmaApiApplication"

FROM amazoncorretto:21

COPY --from=build /app/target/*.jar.original /app/app.jar

RUN jar tf app.jar | grep -E "BOOT-INF/classes/com/velhaguarda/dlemma|META-INF/MANIFEST.MF"

ENV PORT=10000
EXPOSE $PORT

WORKDIR /app

ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=${PORT}"]