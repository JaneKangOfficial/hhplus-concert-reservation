package io.hhplus.concert.presentation.payments.dto.request;

public class PaymentsRequestDTO {

    private Long userId;

    private Long concertId;

    private Long dateId;

    private Long seatId;

    public PaymentsRequestDTO(Long userId, Long concertId, Long dateId, Long seatId) {
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
}
