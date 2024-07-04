package io.hhplus.concert.presentation.controller;


import io.hhplus.concert.application.entity.PaymentStatus;
import io.hhplus.concert.application.entity.ReservationStatus;
import io.hhplus.concert.presentation.dto.request.PaymentRequestDTO;
import io.hhplus.concert.presentation.dto.request.PointRequestDTO;
import io.hhplus.concert.presentation.dto.request.ReservationRequestDTO;
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
    @GetMapping("/token/{userId}")
    public TokenResponseDTO token(@PathVariable Long userId) {
        return new TokenResponseDTO(1L, "token");
    }

    /**
     * 예약 가능 날짜 API
     */
    @GetMapping("/date")
    public DateResponseDTO date(@RequestParam Long concertId) {
        List<Long> availableDatesId = Arrays.asList(1L, 2L);
        List<LocalDate> availableDates = Arrays.asList(LocalDate.of(2024, 6, 20), LocalDate.of(2024, 6, 21));
        return new DateResponseDTO(availableDatesId, 1L, availableDates);
    }

    /**
     * 예약 가능 좌석 API
     */
    @GetMapping("/seat")
    public SeatResponseDTO seat(@RequestParam Long concertId, @RequestParam Long dateId) {
        List<Long> availableSeats = Arrays.asList(1L, 2L, 3L);
        return new SeatResponseDTO(1L, 1L, availableSeats);
    }

    /**
     * 좌석 예약 요청 API
     */
    @PostMapping("/reservation")
    public ReservationResponseDTO reservation(@RequestBody ReservationRequestDTO reservationRequestDTO) {
        return new ReservationResponseDTO(1L, 2L, 1L, 3L, 5L, ReservationStatus.APPLY);
    }

    /**
     * 잔액 충전 API
     */
    @PostMapping("/point")
    public PointResponseDTO point(@RequestBody PointRequestDTO pointRequestDTO) {
        return new PointResponseDTO(1000);
    }

    /**
     * 잔액 조회 API
     */
    @GetMapping("/balance")
    public PointResponseDTO balance(@RequestParam Long userId) {
        return new PointResponseDTO(10000);
    }

    /**
     * 결제 API
     */
    @PostMapping("/payment")
    public PaymentResponseDTO payment(@RequestBody PaymentRequestDTO paymentRequestDTO) {
        return new PaymentResponseDTO(1L, 1L, 1L, 1L, 1L, 10000, PaymentStatus.COMPLETE);
    }

}
