package com.example.rabbitmq.topic;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;

//@Component
//@RabbitListener(bindings = @QueueBinding(
//        exchange = @Exchange(value = "topic.exchange", type = ExchangeTypes.TOPIC),
//        value = @Queue(value = "topic.info.queue", autoDelete = "true"),
//        key = "*.log.info"
//))
public class InfoReceiver {

    @RabbitHandler
    public void process(String msg) {
        System.out.println("InfoReceiver" + msg);
    }
}
