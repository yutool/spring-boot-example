package com.ankoye.rocketmq.service.impl;

import com.ankoye.rocketmq.config.MqParamConfig;
import com.ankoye.rocketmq.service.RocketMqService;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RocketMqServiceImpl implements RocketMqService {
    @Resource
    private DefaultMQProducer defaultMQProducer;
    @Resource
    private MqParamConfig mqParamConfig;
    @Override
    public SendResult openAccountMsg(String msgInfo) {
        // 可以不使用Config中的Group
        defaultMQProducer.setProducerGroup(mqParamConfig.rocketGroup);
        SendResult sendResult = null;
        try {
            Message sendMsg = new Message(mqParamConfig.rocketTopic,
                    mqParamConfig.rocketTag,
                    "open_account_key",
                    msgInfo.getBytes());
            sendResult = defaultMQProducer.send(sendMsg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendResult ;
    }
}

