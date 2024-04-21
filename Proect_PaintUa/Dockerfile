#FROM openjdk:latest AS app
FROM sapmachine:latest AS app
LABEL authors="Malakhatka"
# Установка пакета netcat
RUN apt-get update && apt-get install -y netcat

WORKDIR /app
 # Копируем WAR-файл из локальной файловой системы внутрь Docker-образа
COPY target/Proect_PaintUa.jar Proect_PaintUa.jar
# Устанавливаем точку входа для контейнера
#ENTRYPOINT ["java", "-jar", "Proect_PaintUa.jar"]
# Копирование скрипта ожидания зависимостей
COPY wait_for_dependencies.sh /usr/local/bin/wait_for_dependencies.sh
RUN chmod +x /usr/local/bin/wait_for_dependencies.sh

# Точка входа для контейнера
ENTRYPOINT ["/usr/local/bin/wait_for_dependencies.sh"]