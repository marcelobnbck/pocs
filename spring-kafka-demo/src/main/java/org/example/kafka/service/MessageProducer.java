package org.example.kafka.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessageProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public MessageProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional("kafkaTransactionManager")
    public void sendInTransaction(String message) {
        kafkaTemplate.executeInTransaction(t -> {
            t.send("demo-topic", message);
            System.out.println("Sent in tx: " + message);
            return null;
        });
    }

    public void send(String message) {
        kafkaTemplate.send("demo-topic", message);
        System.out.println("Sent: " + message);
    }
}
