package io.hhplus.concert.domain.concerts.business.service;

import io.hhplus.concert.domain.concerts.presentation.dto.response.DatesResponseDTO;
import io.hhplus.concert.domain.concerts.presentation.dto.response.SeatsResponseDTO;
import io.hhplus.concert.domain.reservations.presentation.dto.request.ReservationsRequestDTO;
import io.hhplus.concert.domain.reservations.presentation.dto.response.ReservationsResponseDTO;

import java.util.List;

public interface ConcertsService {

    List<DatesResponseDTO> getDates(Long concertId);

    List<SeatsResponseDTO> getSeats(Long concertId, Long dateId);

    ReservationsResponseDTO reservations(ReservationsRequestDTO reservationsRequestDTO);
}
