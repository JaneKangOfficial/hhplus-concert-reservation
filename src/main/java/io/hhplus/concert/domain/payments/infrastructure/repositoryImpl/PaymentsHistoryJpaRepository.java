package io.hhplus.concert.domain.payments.infrastructure.repositoryImpl;

import io.hhplus.concert.domain.payments.business.entity.PaymentsHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentsHistoryJpaRepository extends JpaRepository<PaymentsHistoryEntity, Long> {
}
