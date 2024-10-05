FROM openjdk:17

RUN groupadd -r appgroup && useradd -r -g appgroup appuser

USER appuser:appgroup

WORKDIR /app

COPY target/template.jar .

EXPOSE 8080

CMD ["java", "-jar", "template.jar"]
