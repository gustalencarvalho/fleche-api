# Etapa base: imagem com Java
FROM openjdk:17-jdk-slim

# Diretório dentro do container para a aplicação
WORKDIR /app

# Baixa o script wait-for-it.sh
RUN apt-get update && apt-get install -y curl && \
    curl -o /app/wait-for-it.sh https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh && \
    chmod +x /app/wait-for-it.sh

# Copia o arquivo .war gerado para o container
COPY target/fleche-api.war /app/fleche-api.war

# Expõe a porta padrão do Spring Boot (altere se necessário)
EXPOSE 8080

# Comando para iniciar o Spring Boot com o .war após aguardar o MySQL
CMD ["./wait-for-it.sh", "mysql:3306", "--", "java", "-jar", "/app/fleche-api.war"]
