package io.hhplus.concert.payment;

import io.hhplus.concert.domain.payments.business.entity.PaymentsEntity;
import io.hhplus.concert.domain.payments.business.entity.PaymentsStatus;
import io.hhplus.concert.domain.payments.business.serviceImpl.PaymentsServiceImpl;
import io.hhplus.concert.domain.payments.infrastructure.repositoryImpl.PaymentsJpaRepository;
import io.hhplus.concert.domain.payments.presentation.dto.request.PaymentsRequestDTO;
import io.hhplus.concert.domain.payments.presentation.dto.response.PaymentsResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentsServiceTest {

    @InjectMocks
    private PaymentsServiceImpl paymentsServiceImpl;

    @Mock
    private PaymentsJpaRepository paymentsJpaRepository;

    @Test
    @DisplayName("결제 테스트")
    public void paymentsTest() throws Exception {
        // given
        Long reservationId = 1L;
        Long userId = 1L;
        Long concertId = 1L;
        Long dateId = 1L;
        Long seatId = 1L;
        Long total = 1000L;
        String token = "token";
        PaymentsEntity paymentsEntity = new PaymentsEntity(reservationId, userId, total, PaymentsStatus.COMPLETE);
        PaymentsRequestDTO paymentsRequestDTO = new PaymentsRequestDTO(reservationId, userId, concertId, dateId, seatId);

        when(paymentsJpaRepository.save(paymentsEntity)).thenReturn(paymentsEntity);

        // when & then
        PaymentsResponseDTO paymentsResponseDTO = paymentsServiceImpl.payments(token, paymentsRequestDTO);

        assertEquals(PaymentsStatus.COMPLETE, paymentsResponseDTO.getStatus());
    }

}
