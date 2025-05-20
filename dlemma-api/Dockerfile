# Fase de construção
FROM maven:3.9-amazoncorretto-21 AS build
WORKDIR /app

# 1. Cache de dependências
COPY pom.xml .
RUN mvn dependency:go-offline -B

# 2. Copia e compila
COPY src ./src
RUN mvn clean package -DskipTests && \
    ls -la target/ && \
    jar tf target/dlemma-api-*.jar | grep -E "BOOT-INF/classes/com/velhaguarda/dlemma|META-INF/MANIFEST.MF"

# Fase de execução
FROM amazoncorretto:21
WORKDIR /app

# 3. Copia o JAR repacoteado
COPY --from=build /app/target/dlemma-api-*.jar ./app.jar

# 4. Verificação opcional (se necessário)
# RUN jar tf app.jar | grep -E "BOOT-INF/classes/com/velhaguarda/dlemma|META-INF/MANIFEST.MF"

# Configuração Render
ENV SPRING_PROFILES_ACTIVE=dev
ENV PORT=10000
EXPOSE $PORT

ENTRYPOINT ["java", "-jar", "app.jar"]