package io.hhplus.concert.presentation.controller;

import io.hhplus.concert.application.entity.PaymentsStatus;
import io.hhplus.concert.application.entity.ReservationsStatus;
import io.hhplus.concert.presentation.dto.request.PaymentsRequestDTO;
import io.hhplus.concert.presentation.dto.request.PointsRequestDTO;
import io.hhplus.concert.presentation.dto.request.ReservationsRequestDTO;
import io.hhplus.concert.presentation.dto.response.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/concert")
public class ConcertController {

    /**
     * 유저 토큰 발급 API
     */
    @GetMapping("/tokens/{userId}")
    public TokensResponseDTO token(@PathVariable Long userId) {
        return new TokensResponseDTO(1L, "token");
    }

    /**
     * 예약 가능 날짜 API
     */
    @GetMapping("/dates")
    public DatesResponseDTO date(@RequestParam Long concertId) {
        List<Long> availableDatesId = Arrays.asList(1L, 2L);
        List<LocalDate> availableDates = Arrays.asList(LocalDate.of(2024, 6, 20), LocalDate.of(2024, 6, 21));
        return new DatesResponseDTO(availableDatesId, 1L, availableDates);
    }

    /**
     * 예약 가능 좌석 API
     */
    @GetMapping("/seats")
    public SeatsResponseDTO seat(@RequestParam Long concertId, @RequestParam Long dateId) {
        List<Long> availableSeats = Arrays.asList(1L, 2L, 3L);
        return new SeatsResponseDTO(1L, 1L, availableSeats);
    }

    /**
     * 좌석 예약 요청 API
     */
    @PostMapping("/reservations")
    public ReservationsResponseDTO reservation(@RequestBody ReservationsRequestDTO reservationsRequestDTO) {
        return new ReservationsResponseDTO(1L, 2L, 1L, 3L, 5L, ReservationsStatus.APPLY);
    }

    /**
     * 잔액 충전 API
     */
    @PostMapping("/points")
    public PointsResponseDTO point(@RequestBody PointsRequestDTO pointsRequestDTO) {
        return new PointsResponseDTO(1000);
    }

    /**
     * 잔액 조회 API
     */
    @GetMapping("/balances")
    public PointsResponseDTO balance(@RequestParam Long userId) {
        return new PointsResponseDTO(10000);
    }

    /**
     * 결제 API
     */
    @PostMapping("/payments")
    public PaymentsResponseDTO payment(@RequestBody PaymentsRequestDTO paymentsRequestDTO) {
        return new PaymentsResponseDTO(1L, 1L, 1L, 1L, 1L, 10000, PaymentsStatus.COMPLETE);
    }

}
