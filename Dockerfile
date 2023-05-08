FROM openjdk:17-jdk-slim
COPY target/city-info-api-0.0.1-SNAPSHOT.jar wiki-service.jar
ENTRYPOINT ["java", "-jar", "/wiki-service.jar"]
EXPOSE 8080