package io.hhplus.concert.reservations;

import io.hhplus.concert.domain.concerts.business.serviceImpl.ConcertsServiceImpl;
import io.hhplus.concert.domain.concerts.infrastructure.repositoryImpl.SeatsJpaRepository;
import io.hhplus.concert.common.status.ReservationsStatus;
import io.hhplus.concert.domain.reservations.infrastructure.respositoryImpl.ReservationsJpaRepository;
import io.hhplus.concert.domain.reservations.presentation.dto.request.ReservationsRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationsServiceTest {

    @InjectMocks
    private ConcertsServiceImpl concertsServiceImpl;

    @Mock
    private ReservationsJpaRepository reservationsJpaRepository;

    @Mock
    private SeatsJpaRepository seatsJpaRepository;

    @Test
    @DisplayName("예약 요청 테스트")
    public void reservationsTest() {
        // Given
        Long userId = 1L;
        Long concertId = 1L;
        Long dateId = 1L;
        Long seatId = 1L;
        ReservationsRequestDTO reservationsRequestDTO = new ReservationsRequestDTO(userId, concertId, dateId, seatId, ReservationsStatus.APPLY);

        when(seatsJpaRepository.findById(seatId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NullPointerException.class, () -> {
            concertsServiceImpl.reservations(reservationsRequestDTO);
        });

    }

}
