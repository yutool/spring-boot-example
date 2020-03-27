package com.example.rabbitmq.topic;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;

//@Component
//@RabbitListener(bindings = @QueueBinding(
//        exchange = @Exchange(value = "topic.exchange", type = ExchangeTypes.TOPIC),
//        value = @Queue(value = "topic.debug.queue", autoDelete = "true"),
//        key = "*.log.*"
//))
public class DebugReceiver {

    @RabbitHandler
    public void process(String msg) {
        System.out.println("DebugReceiver" + msg);
    }
}