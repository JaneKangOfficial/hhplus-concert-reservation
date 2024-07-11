package io.hhplus.concert.reservations;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hhplus.concert.domain.concerts.business.service.ConcertsService;
import io.hhplus.concert.domain.reservations.business.entity.ReservationsStatus;
import io.hhplus.concert.domain.reservations.presentation.controller.ReservationsController;
import io.hhplus.concert.domain.reservations.presentation.dto.request.ReservationsRequestDTO;
import io.hhplus.concert.domain.reservations.presentation.dto.response.ReservationsResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationsController.class)
public class ReservationsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private ConcertsService concertsService;

    @Test
    @DisplayName("예약 요청 테스트")
    public void reservationsTest() throws Exception {
        // given
        ReservationsRequestDTO requestDTO = new ReservationsRequestDTO(1L, 1L, 2L, 2L, ReservationsStatus.APPLY); // 요청 DTO 객체 생성 및 데이터 설정

        when(concertsService.reservations(any(ReservationsRequestDTO.class)))
                .thenReturn(new ReservationsResponseDTO(1L, 1L, 2L, 2L, ReservationsStatus.APPLY));

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/reservations/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.reservationId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.concertId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateId").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.seatId").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("APPLY"));
    }


}
