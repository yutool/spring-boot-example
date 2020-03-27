package com.example.rabbitmq.topic;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;

//@Component
//@RabbitListener(bindings = @QueueBinding(
//        exchange = @Exchange(value = "topic.exchange", type = ExchangeTypes.TOPIC),
//        value = @Queue(value = "topic.error.queue", autoDelete = "true"),
//        key = "*.log.error"
//))
public class ErrorReceiver {

    @RabbitHandler
    public void process(String msg) {
        System.out.println("ErrorReceiver" + msg);
    }
}
