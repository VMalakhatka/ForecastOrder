FROM openjdk:latest AS app
LABEL authors="Malakhatka"
WORKDIR /app
 # Копируем WAR-файл из локальной файловой системы внутрь Docker-образа
COPY target/Proect_Lavka.jar Proect_Lavka.jar
# Устанавливаем точку входа для контейнера
ENTRYPOINT ["java", "-jar", "Proect_Lavka.jar"]