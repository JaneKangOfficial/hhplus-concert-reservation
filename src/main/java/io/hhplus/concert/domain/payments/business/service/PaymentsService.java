package io.hhplus.concert.domain.payments.business.service;

import io.hhplus.concert.domain.payments.presentation.dto.request.PaymentsRequestDTO;
import io.hhplus.concert.domain.payments.presentation.dto.response.PaymentsResponseDTO;

public interface PaymentsService {
    PaymentsResponseDTO payments(String token, PaymentsRequestDTO paymentsRequestDTO);
}
