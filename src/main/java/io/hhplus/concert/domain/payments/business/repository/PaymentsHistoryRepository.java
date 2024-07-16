package io.hhplus.concert.domain.payments.business.repository;

import io.hhplus.concert.domain.payments.business.entity.PaymentsHistoryEntity;

public interface PaymentsHistoryRepository {
    void save(PaymentsHistoryEntity paymentsHistoryEntity);

}
