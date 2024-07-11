package io.hhplus.concert.domain.reservations.presentation.controller;

import io.hhplus.concert.domain.concerts.business.service.ConcertsService;
import io.hhplus.concert.domain.reservations.presentation.dto.request.ReservationsRequestDTO;
import io.hhplus.concert.domain.reservations.presentation.dto.response.ReservationsResponseDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservations")
public class ReservationsController {

    private final ConcertsService concertsService;

    public ReservationsController(ConcertsService concertsService) {
        this.concertsService = concertsService;
    }

    /**
     * 좌석 예약 요청 API
     */
    @PostMapping("/request")
    public ReservationsResponseDTO reservations(@RequestBody ReservationsRequestDTO reservationsRequestDTO) {
        return concertsService.reservations(reservationsRequestDTO);
    }
}
