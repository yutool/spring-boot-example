package com.ankoye.rocketmq.demo.filter.sql;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.TimeUnit;

/**
 * 生产者
 * msg.putUserProperty
 */
public class Producer {
    public static void main(String[] args) throws Exception {
        // 1.创建消息生产者producer，并制定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        // 2.指定Nameserver地址
        producer.setNamesrvAddr("rocketmq-server1:9876;rocketmq-server2:9876");
        // 3.启动producer
        producer.start();

        for (int i = 0; i < 10; i++) {
            // 4.创建消息对象，指定主题Topic、Tag和消息体
            /**
             * 参数一：消息主题Topic
             * 参数二：消息Tag
             * 参数三：消息内容
             */
            Message msg = new Message("FilterSQLTopic", "Tag1", ("Hello World" + i).getBytes());

            msg.putUserProperty("i", String.valueOf(i));

            // 5.发送消息
            SendResult result = producer.send(msg);
            System.out.println("发送结果:" + result);

            // 线程睡1秒
            TimeUnit.SECONDS.sleep(1);
        }

        // 6.关闭生产者producer
        producer.shutdown();
    }
}
