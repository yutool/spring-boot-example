package com.example.rabbitmq.fanout;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(bindings = @QueueBinding(
        exchange = @Exchange(value = "fanout.exchange", type = ExchangeTypes.FANOUT),
        value = @Queue(value = "fanout.sms.queue", autoDelete = "true")
))
public class SmsReceiver {
    @RabbitHandler
    public void process(String msg) {
        System.out.println("SmsReceiver: " + msg);
    }
}
