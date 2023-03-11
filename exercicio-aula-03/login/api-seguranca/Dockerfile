FROM openjdk:11-jdk-slim
WORKDIR /app
COPY api-seguranca/target/api-seguranca-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "/app/api-seguranca-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080