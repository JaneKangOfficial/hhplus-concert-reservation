package io.hhplus.concert.domain.payments.business.message;

import io.hhplus.concert.domain.outbox.infrastructure.entity.OutboxEntity;
import lombok.Data;

@Data
public class PaymentOutbox {

    private Long messageId;

    private String status;

    private String type;

    private String message;

    public PaymentOutbox(Long messageId, String status, String type, String message) {
        this.messageId = messageId;
        this.status = status;
        this.type = type;
        this.message = message;
    }

    public static OutboxEntity convertToEntity(Long messageId, String status, String type, String message) {
        return new OutboxEntity(messageId, status, type, message);
    }

}
