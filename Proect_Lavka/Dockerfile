#FROM openjdk:latest AS app
FROM sapmachine:latest AS app
LABEL authors="Malakhatka"
# Установка необходимых пакетов для L2TP VPN
RUN apt-get update && apt-get install -y strongswan xl2tpd iputils-ping
RUN mkdir -p /var/run/xl2tpd/
COPY vpn-config /etc/vpn-config
WORKDIR /app
COPY target/Proect_Lavka.jar Proect_Lavka.jar
EXPOSE 8083
#ENTRYPOINT ["java", "-jar", "Proect_Lavka.jar"]
# Запуск L2TP VPN перед запуском JAR файла
CMD ipsec start && xl2tpd -D & java -jar /app/Proect_Lavka.jar