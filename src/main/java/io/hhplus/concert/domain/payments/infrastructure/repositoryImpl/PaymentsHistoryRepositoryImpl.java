package io.hhplus.concert.domain.payments.infrastructure.repositoryImpl;

import io.hhplus.concert.domain.payments.business.entity.PaymentsHistoryEntity;
import io.hhplus.concert.domain.payments.business.repository.PaymentsHistoryRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentsHistoryRepositoryImpl implements PaymentsHistoryRepository {

    private final PaymentsHistoryJpaRepository paymentsJpaRepository;

    public PaymentsHistoryRepositoryImpl(PaymentsHistoryJpaRepository paymentsJpaRepository) {
        this.paymentsJpaRepository = paymentsJpaRepository;
    }


    @Override
    public void save(PaymentsHistoryEntity paymentsHistoryEntity) {
        paymentsJpaRepository.save(paymentsHistoryEntity);
    }
}
