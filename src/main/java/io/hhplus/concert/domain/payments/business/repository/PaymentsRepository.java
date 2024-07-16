package io.hhplus.concert.domain.payments.business.repository;

import io.hhplus.concert.domain.payments.business.entity.PaymentsEntity;

public interface PaymentsRepository {

    PaymentsEntity save(PaymentsEntity paymentsEntity);

}
