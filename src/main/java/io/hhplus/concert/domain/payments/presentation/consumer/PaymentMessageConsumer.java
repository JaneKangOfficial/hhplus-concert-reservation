package io.hhplus.concert.domain.payments.presentation.consumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.hhplus.concert.domain.payments.business.message.PaymentMessageOutboxWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentMessageConsumer {

    private final PaymentMessageOutboxWriter paymentMessageOutboxWriter;

    public PaymentMessageConsumer(PaymentMessageOutboxWriter paymentMessageOutboxWriter) {
        this.paymentMessageOutboxWriter = paymentMessageOutboxWriter;
    }

    // payment-topic에서 메시지를 자동으로 수신하여 처리
    @KafkaListener(topics = "${spring.kafka.template.default-topic}", groupId = "my-group")
    public void consume(ConsumerRecord<String, String> record, Acknowledgment ack) {
        try {
            log.debug("Consumed message: {}", record.value());

            // 메시지의 값을 JSON으로 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode messageNode = objectMapper.readTree(record.value());

            // JSON에서 ID 추출
            Long paymentId = Long.valueOf(messageNode.get("paymentId").asText());

            // 메세지 발행 성공 outbox published 로 상태 변경
            paymentMessageOutboxWriter.complete(paymentId);

            // 메시지 처리 후 오프셋 수동 커밋
            ack.acknowledge();
        } catch (Exception e) {
            log.debug("Error Consumed message: " + e.getMessage());
        }

    }

}
