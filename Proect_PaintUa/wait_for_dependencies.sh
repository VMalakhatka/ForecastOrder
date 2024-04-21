#!/bin/bash

# Функция для ожидания доступности зависимостей
wait_for_dependencies() {
    # Ждем, пока PostgreSQL будет доступен по порту 5432
    until nc -z -w 1 db 5432; do
        echo "Waiting for PostgreSQL to become available..."
        sleep 1
    done

    # Ждем, пока RabbitMQ будет доступен по порту 5672
    until nc -z -w 1 rabbitmq 5672; do
        echo "Waiting for RabbitMQ to become available..."
        sleep 1
    done
}

# Вызываем функцию для ожидания зависимостей
wait_for_dependencies

# Запускаем ваше Java приложение
java -jar Proect_PaintUa.jar