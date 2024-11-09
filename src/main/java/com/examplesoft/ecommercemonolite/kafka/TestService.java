package com.examplesoft.ecommercemonolite.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @EventListener(value = ApplicationReadyEvent.class)
    public void startTesting(){
        log.warn("Mesaj Gönderimi Başlıyor");
        kafkaTemplate.send("first-topic", "İlk Kafka mesajı gönderiliyor");
    }

    @KafkaListener(topics = "first-topic", groupId = "consumer-group", containerFactory = "kafkaListenerContainerFactory")
    public void getMessage(String message){
        log.info("Mesaj Başarıyla Alındı {}", message);
    }
}
