package io.hhplus.concert.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hhplus.concert.common.status.PaymentsStatus;
import io.hhplus.concert.domain.payments.business.service.PaymentsService;
import io.hhplus.concert.domain.payments.presentation.controller.PaymentsController;
import io.hhplus.concert.domain.payments.presentation.dto.request.PaymentsRequestDTO;
import io.hhplus.concert.domain.payments.presentation.dto.response.PaymentsResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentsController.class)
public class PaymentsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private PaymentsService paymentsService;

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
        PaymentsRequestDTO paymentsRequestDTO = new PaymentsRequestDTO(reservationId, userId, concertId, dateId, seatId);
        given(paymentsService.payments(token, paymentsRequestDTO)).willReturn(new PaymentsResponseDTO(reservationId, userId, total, PaymentsStatus.COMPLETE));

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/payments/request")
                        .header("token", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentsRequestDTO)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }


}
