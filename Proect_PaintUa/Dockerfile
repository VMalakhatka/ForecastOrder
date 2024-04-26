
FROM sapmachine:latest AS app
LABEL authors="Malakhatka"

RUN apt-get update && apt-get install -y netcat

WORKDIR /app
COPY target/Proect_PaintUa.jar Proect_PaintUa.jar

COPY wait_for_dependencies.sh /usr/local/bin/wait_for_dependencies.sh
RUN chmod +x /usr/local/bin/wait_for_dependencies.sh

ENTRYPOINT ["/usr/local/bin/wait_for_dependencies.sh"]