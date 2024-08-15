package io.hhplus.concert.domain.payments.business.message;

import io.hhplus.concert.domain.outbox.infrastructure.entity.OutboxEntity;
import io.hhplus.concert.domain.payments.business.event.PaymentEvent;

import java.util.List;

public interface PaymentMessageOutboxWriter {
    void save(OutboxEntity outboxEntity);

    void complete(Long messageId);

    String findStatusByMessageId(Long messageId);

    List<PaymentEvent> getOutboxInit(String status);
}
