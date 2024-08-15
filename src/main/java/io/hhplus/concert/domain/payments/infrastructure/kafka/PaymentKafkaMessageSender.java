package io.hhplus.concert.domain.payments.infrastructure.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hhplus.concert.domain.payments.business.event.PaymentEvent;
import io.hhplus.concert.domain.payments.business.message.PaymentMessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentKafkaMessageSender implements PaymentMessageSender {

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.template.default-topic}")
    private String TOPIC;

    public PaymentKafkaMessageSender(ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate) {
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    // 메세지 발행
    public void send(PaymentEvent paymentEvent) {
        Long partitionKey = paymentEvent.getUserId(); // UserId를 파티션 키로 사용
        String paymentEventJson = null;
        try {
            paymentEventJson = objectMapper.writeValueAsString(paymentEvent); // PaymentEvent 객체를 JSON 문자열로 변환

            // 카프카 토픽에 전송
            kafkaTemplate.send(TOPIC, String.valueOf(partitionKey), paymentEventJson);
        } catch (Exception e) {
            log.debug("Error Produced message: " + e.getMessage());
        }

    }
}
