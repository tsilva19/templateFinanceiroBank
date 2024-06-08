FROM openjdk:17-jdk-alpine

# Adicionar Dockerize
ADD https://github.com/jwilder/dockerize/releases/download/v0.6.1/dockerize-linux-amd64-v0.6.1.tar.gz /tmp/
RUN tar -C /usr/local/bin -xzvf /tmp/dockerize-linux-amd64-v0.6.1.tar.gz

# Copiar o JAR da aplicação
COPY ./target/financeiroBank-0.0.1-SNAPSHOT.jar /app/financeiroBank.jar

# Comando para aguardar o LocalStack e iniciar a aplicação
CMD ["sh", "-c", "dockerize -wait tcp://localstack:4566 -timeout 60s java -jar /app/financeiroBank.jar"]
