package io.hhplus.concert.domain.outbox.infrastructure.repositoryImpl;

import io.hhplus.concert.domain.outbox.infrastructure.entity.OutboxEntity;
import io.hhplus.concert.domain.payments.business.event.PaymentEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OutboxJpaRepository extends JpaRepository<OutboxEntity, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE OutboxEntity o SET o.status = 'PUBLISHED' WHERE o.messageId = :messageId")
    void complete(@Param("messageId") Long messageId);

    @Query("SELECT o.status FROM OutboxEntity o WHERE o.messageId = :messageId")
    String findStatusByMessageId(@Param("messageId") Long messageId);

    @Query("SELECT o.messageId FROM OutboxEntity o WHERE o.status = :status ")
    List<PaymentEvent> findByStatus(@Param("status") String status);
}
