# Estágio 1: Build da Aplicação com Maven
# Usamos uma imagem que já tem Maven e JDK para compilar o projeto.
FROM maven:3.9-eclipse-temurin-21 AS builder

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o pom.xml primeiro para aproveitar o cache do Docker.
# As dependências só serão baixadas novamente se o pom.xml mudar.
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o resto do código fonte
COPY src ./src

# Compila o projeto e gera o arquivo .jar, pulando os testes
RUN mvn package -DskipTests


# Estágio 2: Imagem Final de Execução
# Usamos uma imagem JRE (Java Runtime Environment), que é mais leve que o JDK completo.
# Use a mesma versão Java do seu projeto (no seu caso, 25).
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copia o arquivo .jar gerado no estágio anterior para a imagem final
COPY --from=builder /app/target/*.jar app.jar

# Expõe a porta da aplicação (padrão do Spring Boot)
EXPOSE 8080

# Expõe a porta para o debugger remoto
EXPOSE 5005

# Comando para iniciar a aplicação quando o contêiner for executado.
# A variável JAVA_TOOL_OPTIONS será definida no compose.yaml para ativar o debug.
ENTRYPOINT ["java", "-jar", "app.jar"]