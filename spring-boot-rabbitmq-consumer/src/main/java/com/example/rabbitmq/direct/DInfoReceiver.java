package com.example.rabbitmq.direct;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

//@Component
//@RabbitListener(bindings = @QueueBinding(
//        exchange = @Exchange(value = "${mq.config.exchange}", type = ExchangeTypes.DIRECT),
//        value = @Queue(value = "${mq.config.queue.info}", autoDelete = "true"),
//        key = "${mq.config.info.routing.key}"
//))
public class DInfoReceiver {

    @RabbitHandler
    public void process(String msg) {
        System.out.println("InfoReceiver: " + msg);
    }
}
