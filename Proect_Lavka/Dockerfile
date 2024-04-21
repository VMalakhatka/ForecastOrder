FROM openjdk:latest AS app
LABEL authors="Malakhatka"
WORKDIR /app
COPY target/Proect_Lavka.jar Proect_Lavka.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "Proect_Lavka.jar"]