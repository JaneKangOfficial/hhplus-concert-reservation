package io.hhplus.concert.domain.outbox.infrastructure.repositoryImpl;

import io.hhplus.concert.domain.outbox.infrastructure.entity.OutboxEntity;
import io.hhplus.concert.domain.payments.business.event.PaymentEvent;
import io.hhplus.concert.domain.payments.business.message.PaymentMessageOutboxWriter;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OutboxRepositoryImpl implements PaymentMessageOutboxWriter {

    private final OutboxJpaRepository outboxJpaRepository;

    public OutboxRepositoryImpl(OutboxJpaRepository outboxJpaRepository) {
        this.outboxJpaRepository = outboxJpaRepository;
    }

    @Override
    public void save(OutboxEntity outboxEntity) {
        outboxJpaRepository.save(outboxEntity);
    }

    @Override
    public void complete(Long messageId) {
        outboxJpaRepository.complete(messageId);
    }

    @Override
    public String findStatusByMessageId(Long messageId) {
        return outboxJpaRepository.findStatusByMessageId(messageId);
    }

    @Override
    public List<PaymentEvent> getOutboxInit(String status) {
        return outboxJpaRepository.findByStatus(status);
    }


}
