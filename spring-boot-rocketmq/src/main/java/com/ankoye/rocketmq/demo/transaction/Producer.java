package com.ankoye.rocketmq.demo.transaction;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.concurrent.TimeUnit;

/**
 * 1.new TransactionMQProducer
 * 2.producer.setTransactionListener
 * 3.producer.sendMessageInTransaction
 *
 * 事务消息状态：提交状态、回滚状态、中间状态：
 *  TransactionStatus.CommitTransaction: 提交事务，它允许消费者消费此消息。
 *  TransactionStatus.RollbackTransaction: 回滚事务，它代表该消息将被删除，不允许被消费。
 *  TransactionStatus.Unknown: 中间状态，它代表需要检查消息队列来确定状态。
 */
public class Producer {
    public static void main(String[] args) throws Exception {
        // 1.创建消息生产者producer，并制定生产者组名
        TransactionMQProducer producer = new TransactionMQProducer("group1");
        // 2.指定Nameserver地址
        producer.setNamesrvAddr("rocketmq-server1:9876;rocketmq-server2:9876");

        // 添加事务监听器
        producer.setTransactionListener(new TransactionListener() {
            /**
             * 在该方法中执行本地事务
             * @param msg half消息
             * @param arg
             */
            @Override
            public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                if (StringUtils.equals("TAGA", msg.getTags())) {
                    return LocalTransactionState.COMMIT_MESSAGE;
                } else if (StringUtils.equals("TAGB", msg.getTags())) {
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                } else if (StringUtils.equals("TAGC", msg.getTags())) {
                    return LocalTransactionState.UNKNOW;
                }
                return LocalTransactionState.UNKNOW;
            }

            /**
             * LocalTransactionState.UNKNOW时MQ进行消息事务状态回查
             * @param msg half消息
             */
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                System.out.println("消息的Tag: " + msg.getTags());
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });

        // 3.启动producer
        producer.start();

        String[] tags = {"TAGA", "TAGB", "TAGC"};
        for (int i = 0; i < 3; i++) {
            // 4.创建消息对象，指定主题Topic、Tag和消息体
            /**
             * 参数一：消息主题Topic
             * 参数二：消息Tag
             * 参数三：消息内容
             */
            Message msg = new Message("TransactionTopic", tags[i], ("Hello World" + i).getBytes());

            // 5.发送消息  sendMessageInTransaction 发送事物消息
            SendResult result = producer.sendMessageInTransaction(msg, null);
            System.out.println("发送结果:" + result);

            // 线程睡1秒
            TimeUnit.SECONDS.sleep(1);
        }

        // 6.关闭生产者producer - 需要回差不要关闭
        // producer.shutdown();
    }
}
