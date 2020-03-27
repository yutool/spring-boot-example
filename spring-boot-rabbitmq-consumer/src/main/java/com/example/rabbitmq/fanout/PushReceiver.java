package com.example.rabbitmq.fanout;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(bindings = @QueueBinding(
        exchange = @Exchange(value = "fanout.exchange", type = ExchangeTypes.FANOUT),
        value = @Queue(value = "fanout.push.queue", autoDelete = "true")
))
public class PushReceiver {
    @RabbitHandler
    public void process(String msg) {
        System.out.println("PushReceiver: " + msg);
    }
}
