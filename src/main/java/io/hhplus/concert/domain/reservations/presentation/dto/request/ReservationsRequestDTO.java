package io.hhplus.concert.domain.reservations.presentation.dto.request;

import io.hhplus.concert.domain.reservations.business.entity.ReservationsStatus;
import io.hhplus.concert.domain.reservations.infrastructure.entity.ReservationsEntity;

public class ReservationsRequestDTO {

    private Long userId;

    private Long concertId;

    private Long dateId;

    private Long seatId;

    private ReservationsStatus status;

    public ReservationsRequestDTO(Long userId, Long concertId, Long dateId, Long seatId, ReservationsStatus status) {
        this.userId = userId;
        this.concertId = concertId;
        this.dateId = dateId;
        this.seatId = seatId;
        this.status = status;
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

    public static ReservationsEntity convertToEntity(ReservationsRequestDTO reservationsRequestDTO) {
        return new ReservationsEntity(reservationsRequestDTO.getUserId(), reservationsRequestDTO.getConcertId(), reservationsRequestDTO.getDateId(), reservationsRequestDTO.getSeatId(), reservationsRequestDTO.getStatus());
    }
}
