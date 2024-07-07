package io.hhplus.concert.presentation.reservations.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.hhplus.concert.infrastructure.entity.ReservationsStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationsResponseDTO {

    private Long reservationId;

    private Long userId;

    private Long concertId;

    private Long dateId;

    private Long seatId;

    private ReservationsStatus status;


    public ReservationsResponseDTO(Long reservationId, Long userId, Long concertId, Long dateId, Long seatId, ReservationsStatus status) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.concertId = concertId;
        this.dateId = dateId;
        this.seatId = seatId;
        this.status = status;
    }

    public ReservationsResponseDTO(ReservationsStatus status) {
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

    public ReservationsStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationsStatus status) {
        this.status = status;
    }
}
