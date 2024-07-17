package io.hhplus.concert.domain.concerts.presentation.controller;

import io.hhplus.concert.domain.concerts.business.service.ConcertsService;
import io.hhplus.concert.domain.concerts.presentation.dto.response.ConcertsResponseDTO;
import io.hhplus.concert.domain.concerts.presentation.dto.response.DatesResponseDTO;
import io.hhplus.concert.domain.concerts.presentation.dto.response.SeatsResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/concerts")
public class ConcertsController {

    private final ConcertsService concertsService;

    public ConcertsController(ConcertsService concertsService) {
        this.concertsService = concertsService;
    }

    /**
     * 콘서트 조회 API
     */
    @GetMapping("/")
    public List<ConcertsResponseDTO> getConcerts() {
        return concertsService.getConcerts();
    }

    /**
     * 예약 가능 날짜 API
     */
    @GetMapping("/{concertId}/dates")
    public List<DatesResponseDTO> getDates(@PathVariable Long concertId) {
        return concertsService.getDates(concertId);
    }

    /**
     * 예약 가능 좌석 API
     */
    @GetMapping("/{concertId}/{dateId}/seats")
    public List<SeatsResponseDTO> getSeats(@PathVariable Long concertId, @PathVariable Long dateId) {
        return concertsService.getSeats(concertId, dateId);
    }


}
