package com.example.rabbitmq.direct;

import com.example.rabbitmq.RabbitmqApplication;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitmqApplication.class)
public class TestSender {
    @Autowired
    private Sender sender;

    @Test
    public void testDirect() throws Exception {
        while (true) {
            sender.directSend("hello ");
            Thread.sleep(2000);
        }
    }

    @Test
    public void testTopic() throws Exception {
        while (true) {
            sender.topicSend("hello ");
            Thread.sleep(2000);
        }
    }

    @Test
    public void testFanout() throws Exception {
        while (true) {
            sender.fanoutSend("hello ");
            Thread.sleep(2000);
        }
    }
}
