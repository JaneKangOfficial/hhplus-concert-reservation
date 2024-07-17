package io.hhplus.concert.domain.payments.infrastructure.repositoryImpl;

import io.hhplus.concert.domain.payments.infrastructure.entity.PaymentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentsJpaRepository extends JpaRepository<PaymentsEntity, Long> {

}
