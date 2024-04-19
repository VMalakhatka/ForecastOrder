FROM openjdk:latest AS app
LABEL authors="Malakhatka"
WORKDIR /app
 # Копируем WAR-файл из локальной файловой системы внутрь Docker-образа
COPY target/Proect_PaintUa.war Proect_PaintUa.war
# Устанавливаем точку входа для контейнера
ENTRYPOINT ["java", "-jar", "Proect_PaintUa.war"]