package io.hhplus.concert.concert;

import io.hhplus.concert.domain.concerts.business.repository.ConcertsRepository;
import io.hhplus.concert.domain.concerts.business.serviceImpl.ConcertsServiceImpl;
import io.hhplus.concert.domain.concerts.infrastructure.entity.DatesEntity;
import io.hhplus.concert.domain.concerts.presentation.dto.response.DatesResponseDTO;
import io.hhplus.concert.domain.concerts.presentation.dto.response.SeatsResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ConcertServiceTest {

    @InjectMocks
    private ConcertsServiceImpl concertsServiceImpl;

    @Mock
    private ConcertsRepository concertsRepository;

    @Test
    @DisplayName("예약 가능 날짜 조회")
    void getDates() {
        // given
        Long concertId = 1L;
        DatesEntity datesEntity1 = new DatesEntity(1L, concertId, LocalDate.of(2024, 6, 20));
        DatesEntity datesEntity2 = new DatesEntity(2L, concertId, LocalDate.of(2024, 6, 21));
        List<DatesEntity> mockDatesEntityList = Arrays.asList(datesEntity1, datesEntity2);

        given(concertsRepository.findByConcertId(concertId)).willReturn(mockDatesEntityList);

        // when
        List<DatesResponseDTO> result = concertsServiceImpl.getDates(concertId);

        // then
        assertEquals(2, result.size()); // 반환된 리스트 크기 2인지 확인

        assertEquals(1L, result.get(0).getConcertOptionId());
        assertEquals(concertId, result.get(0).getConcertId());
        assertEquals(LocalDate.of(2024, 6, 20), result.get(0).getConcertDate());

        assertEquals(2L, result.get(1).getConcertOptionId());
        assertEquals(concertId, result.get(1).getConcertId());
        assertEquals(LocalDate.of(2024, 6, 21), result.get(1).getConcertDate());

    }

    @Test
    @DisplayName("예약 가능 좌석 조회")
    void getSeats() {
        // given
        Long concertId = 1L;
        Long dateId = 1L;
        Long seatNum1 = 100L;
        Long seatNum2 = 200L;
        List<Long> numList = Arrays.asList(seatNum1, seatNum2);

        given(concertsRepository.findSeatNumbersByConcertIdAndDateId(concertId, dateId)).willReturn(numList);

        // when
        List<SeatsResponseDTO> result = concertsServiceImpl.getSeats(concertId, dateId);

        // then
        assertEquals(2, result.size()); // 반환된 리스트 크기 2인지 확인

        assertEquals(seatNum1, result.get(0).getSeatNum());
        assertEquals(seatNum2, result.get(1).getSeatNum());

    }


}
