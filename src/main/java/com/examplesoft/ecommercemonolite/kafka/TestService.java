package com.examplesoft.ecommercemonolite.kafka;

import com.examplesoft.ecommercemonolite.domain.campaign.dto.CampaignDto;
import com.examplesoft.ecommercemonolite.domain.product.dto.ProductDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @EventListener(value = ApplicationReadyEvent.class)
    public void startTesting() {
        log.warn("Mesaj Gönderimi Başlıyor");
        kafkaTemplate.send("first-topic", "İlk Kafka mesajı gönderiliyor");
        ProductDto productDto = ProductDto.builder()
                .name("ürün")
                .originalPrice(BigDecimal.valueOf(150))
                .build();
        kafkaTemplate.send("second-topic", productDto);
    }

    @KafkaListener(topics = "first-topic", groupId = "consumer-group", containerFactory = "kafkaListenerContainerFactory")
    public void getMessage(String message) {
        log.info("Mesaj Başarıyla Alındı: {}", message);
    }

    @KafkaListener(topics = "second-topic", groupId = "consumer-group", containerFactory = "kafkaListenerContainerFactory")
    public void getProductMessage(ProductDto productDto) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String objStr = objectMapper.writeValueAsString(productDto);
            log.info("Ürün Mesajı Başarıyla Alındı: {}", objStr);
        } catch (JsonProcessingException e) {
            log.error("Başarısız!");
        }
    }
}
