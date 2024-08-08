package io.hhplus.concert.domain.payments.business.event;

import io.hhplus.concert.domain.payments.infrastructure.entity.PaymentsHistoryEntity;

public interface PaymentEventPublisher {
    void savePaymentHistory(PaymentsHistoryEntity paymentsHistoryEntity);
}
