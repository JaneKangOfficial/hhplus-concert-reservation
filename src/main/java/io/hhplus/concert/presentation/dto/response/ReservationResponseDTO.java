package io.hhplus.concert.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.hhplus.concert.application.entity.ReservationStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationResponseDTO {

    private Long reservationId;

    private Long userId;

    private Long concertId;

    private Long dateId;

    private Long seatId;

    private ReservationStatus status;


    public ReservationResponseDTO(Long reservationId, Long userId, Long concertId, Long dateId, Long seatId, ReservationStatus status) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.concertId = concertId;
        this.dateId = dateId;
        this.seatId = seatId;
        this.status = status;
    }

    public ReservationResponseDTO(ReservationStatus status) {
        this.status = status;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getConcertId() {
        return concertId;
    }

    public void setConcertId(Long concertId) {
        this.concertId = concertId;
    }

    public Long getDateId() {
        return dateId;
    }

    public void setDateId(Long dateId) {
        this.dateId = dateId;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
}
