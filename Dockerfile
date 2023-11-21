# syntax=docker/dockerfile:1

FROM eclipse-temurin:17.0.9_9-jre-ubi9-minimal

WORKDIR /app

COPY magicQoven-0.0.1-SNAPSHOT.war /app/magicQoven.war

CMD ["java", "-jar", "/app/magicQoven-0.0.1-SNAPSHOT.war"]