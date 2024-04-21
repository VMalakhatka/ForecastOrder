#!/bin/bash

wait_for_dependencies() {
    until nc -z -w 1 db 5432; do
        echo "Waiting for PostgreSQL to become available..."
        sleep 1
    done

    until nc -z -w 1 rabbitmq 5672; do
        echo "Waiting for RabbitMQ to become available..."
        sleep 1
    done
}

wait_for_dependencies

java -jar Proect_PaintUa.jar