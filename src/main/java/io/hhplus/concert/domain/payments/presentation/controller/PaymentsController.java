package io.hhplus.concert.domain.payments.presentation.controller;

import io.hhplus.concert.domain.payments.business.service.PaymentsService;
import io.hhplus.concert.domain.payments.presentation.dto.request.PaymentsRequestDTO;
import io.hhplus.concert.domain.payments.presentation.dto.response.PaymentsResponseDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentsController {

    private final PaymentsService paymentsService;

    public PaymentsController(PaymentsService paymentsService) {
        this.paymentsService = paymentsService;
    }

    /**
     * 결제 API
     */
    @PostMapping("/request")
    public PaymentsResponseDTO payments(@RequestHeader("token") String token, @RequestBody PaymentsRequestDTO paymentsRequestDTO) {
        return paymentsService.payments(token, paymentsRequestDTO);
    }

}
