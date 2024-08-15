package io.hhplus.concert.domain.payments.business.service;

import io.hhplus.concert.domain.payments.business.event.PaymentEvent;
import io.hhplus.concert.domain.payments.presentation.dto.request.PaymentsRequestDTO;
import io.hhplus.concert.domain.payments.presentation.dto.response.PaymentsResponseDTO;
import io.hhplus.concert.domain.points.business.event.PointEvent;

public interface PaymentsService {
    PaymentsResponseDTO payments(String token, PaymentsRequestDTO paymentsRequestDTO);

    void savePaymentHistory(PaymentEvent paymentEvent);

    void calculatePoint(PointEvent pointEvent);
}
