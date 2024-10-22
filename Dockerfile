FROM maven:3.9.6-eclipse-temurin-22-jammy

COPY pom.xml .

COPY src ./src

RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-slim

COPY --from=0 /target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]