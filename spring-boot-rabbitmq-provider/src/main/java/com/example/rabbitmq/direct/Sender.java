package com.example.rabbitmq.direct;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Sender {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${mq.config.exchange}")
    private String exchange;

    @Value("${mq.config.info.routing.key}")
    private String logKey;
    @Value("${mq.config.error.routing.key}")
    private String errorKey;

    // 测试DIRECT
    public void directSend(String msg) {
        amqpTemplate.convertAndSend(exchange, logKey, msg);
        amqpTemplate.convertAndSend(exchange, errorKey, msg);
    }

    // 测试 TOPIC
    public void topicSend(String msg) {
        amqpTemplate.convertAndSend("topic.exchange", "user.log.info", "user.log.info" + msg);
        amqpTemplate.convertAndSend("topic.exchange", "user.log.error", "user.log.error" + msg);
        amqpTemplate.convertAndSend("topic.exchange", "user.log.debug", "user.log.debug" + msg);

        amqpTemplate.convertAndSend("topic.exchange", "admin.log.info", "admin.log.info" + msg);
        amqpTemplate.convertAndSend("topic.exchange", "admin.log.error", "admin.log.error" + msg);
        amqpTemplate.convertAndSend("topic.exchange", "admin.log.debug", "admin.log.debug" + msg);

    }

    // 测试广播
    public void fanoutSend(String msg) {
        amqpTemplate.convertAndSend("fanout.exchange", "", "fanout " + msg);
    }
}
