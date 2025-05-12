# D-lemma API

API em Java com Spring Boot para gerenciamento de usuários, autenticação e outras rotas.

## ✅ Requisitos

- Java 21
- Maven
- PostgreSQL rodando localmente

## 🚀 Como rodar o projeto

1. **Clone o repositório:**

```bash
git clone https://github.com/seu-usuario/d-lemma-api.git
cd d-lemma-api

```
2. **Configure o arquivo "application.properties"**
No caminho src/main/resources/application.properties, insira os dados do seu banco PostgreSQL (siga o exemplo do "application-example.properties").

3. Rode a aplicação:
- Via terminal:
```bash
./mvnw spring-boot:run
```
- ou via IDE (IntelliJ, VSCode, etc):
```bash
Vá até src/main/java/**/dlemma/DLemmaApiApplication.java
Execute com Run
```

## Observações
1. O banco de dados 'dlemma' precisa existir no PostgreSQL antes de rodar.
2. As requisições podem ser testadas via Postman ou Insomnia.
