package io.hhplus.concert.domain.payments.infrastructure.repositoryImpl;

import io.hhplus.concert.domain.payments.business.entity.PaymentsEntity;
import io.hhplus.concert.domain.payments.business.repository.PaymentsRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentsRepositoryImpl implements PaymentsRepository {

    private final PaymentsJpaRepository paymentsJpaRepository;

    public PaymentsRepositoryImpl(PaymentsJpaRepository paymentsJpaRepository) {
        this.paymentsJpaRepository = paymentsJpaRepository;
    }

    @Override
    public PaymentsEntity save(PaymentsEntity paymentsEntity) {
        return paymentsJpaRepository.save(paymentsEntity);
    }
}
