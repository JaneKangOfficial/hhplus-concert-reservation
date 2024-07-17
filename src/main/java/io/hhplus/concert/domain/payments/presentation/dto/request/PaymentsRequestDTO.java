package io.hhplus.concert.domain.payments.presentation.dto.request;

import io.hhplus.concert.common.status.PaymentsStatus;
import io.hhplus.concert.domain.payments.infrastructure.entity.PaymentsEntity;

public class PaymentsRequestDTO {

    private Long reservationId;

    private Long userId;

    private Long concertId;

    private Long dateId;

    private Long seatId;

    public PaymentsRequestDTO(Long reservationId, Long userId, Long concertId, Long dateId, Long seatId) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.concertId = concertId;
        this.dateId = dateId;
        this.seatId = seatId;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public Long getDateId() {
        return dateId;
    }

    public void setDateId(Long dateId) {
        this.dateId = dateId;
    }

    public Long getConcertId() {
        return concertId;
    }

    public void setConcertId(Long concertId) {
        this.concertId = concertId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public PaymentsEntity convertToEntity(PaymentsRequestDTO paymentsRequestDTO, Long price, PaymentsStatus status) {
        return new PaymentsEntity(paymentsRequestDTO.getReservationId(), paymentsRequestDTO.getUserId(), price, status);
    }
}
