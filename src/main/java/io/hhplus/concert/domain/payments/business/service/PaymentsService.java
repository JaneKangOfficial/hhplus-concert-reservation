package io.hhplus.concert.domain.payments.business.service;

import io.hhplus.concert.domain.payments.infrastructure.entity.PaymentsHistoryEntity;
import io.hhplus.concert.domain.payments.presentation.dto.request.PaymentsRequestDTO;
import io.hhplus.concert.domain.payments.presentation.dto.response.PaymentsResponseDTO;
import io.hhplus.concert.domain.points.infrastructure.entity.PointHistoryEntity;

public interface PaymentsService {
    PaymentsResponseDTO payments(String token, PaymentsRequestDTO paymentsRequestDTO);

    void savePaymentHistory(PaymentsHistoryEntity paymentsHistoryEntity);

    void calculatePoint(PointHistoryEntity pointHistoryEntity);
}
