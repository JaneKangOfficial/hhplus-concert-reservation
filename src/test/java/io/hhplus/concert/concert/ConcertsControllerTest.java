package io.hhplus.concert.concert;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hhplus.concert.domain.concerts.business.service.ConcertsService;
import io.hhplus.concert.domain.concerts.presentation.controller.ConcertsController;
import io.hhplus.concert.domain.concerts.presentation.dto.response.DatesResponseDTO;
import io.hhplus.concert.domain.concerts.presentation.dto.response.SeatsResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConcertsController.class)
public class ConcertsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ConcertsService concertsService;

    @Test
    @DisplayName("예약 가능 날짜 테스트")
    public void datesTest() throws Exception {
        // given
        Long concertId = 1L;
        given(concertsService.getDates(concertId)).willReturn(Arrays.asList(
                new DatesResponseDTO(1L, concertId, LocalDate.of(2024, 6, 20)),
                new DatesResponseDTO(2L, concertId, LocalDate.of(2024, 6, 21))
        ));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/concerts/1/dates"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].concertOptionId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].concertId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].concertDate").value("2024-06-20"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].concertOptionId").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].concertId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].concertDate").value("2024-06-21"));
    }

    @Test
    @DisplayName("예약 가능 좌석 테스트")
    public void seatsTest() throws Exception {
        // given
        Long concertId = 1L;
        Long dateId = 1L;
        Long userId = 1L;
        given(concertsService.getSeats(concertId, dateId, userId)).willReturn(Arrays.asList(
                new SeatsResponseDTO(100L),
                new SeatsResponseDTO(200L)
        ));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/concerts/1/1/seats"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].seatNum").value(100L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].seatNum").value(200L));
    }

}
