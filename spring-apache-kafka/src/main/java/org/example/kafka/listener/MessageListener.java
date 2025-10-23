package org.example.kafka.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    @KafkaListener(topics = "kafka-topic", groupId = "group1")
    public void listen(String message) {
        System.out.println("Received: " + message);
    }
}
