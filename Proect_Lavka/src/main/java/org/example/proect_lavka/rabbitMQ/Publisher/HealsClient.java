package org.example.proect_lavka.rabbitMQ.Publisher;


import org.example.proect_lavka.config.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
public class HealsClient {

    @RabbitListener(queues = RabbitConfig.QUEUE_HEALS)
    public Boolean healsResponse(@Payload String message){
        return true;
    }
}
