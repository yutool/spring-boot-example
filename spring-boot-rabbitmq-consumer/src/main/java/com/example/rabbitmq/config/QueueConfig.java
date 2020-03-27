package com.example.rabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

    /**
     * 创建消息队列，一种方式
     * Sender: amqpTemplate.convertAndSend("hello-queue", "msg");
     * Receiver: @RabbitListener(queues="hello-queue")
     * @return
     */
    @Bean
    public Queue createQueue() {
        return new Queue("hello-queue");
    }
}
