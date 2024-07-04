package io.hhplus.concert.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.hhplus.concert.application.entity.PaymentStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentResponseDTO {

    private Long paymentId;

    private Long userId;

    private Long concertId;

    private Long dateId;

    private Long seatId;

    private Integer total;

    private PaymentStatus status;

    public PaymentResponseDTO(Long paymentId, Long userId, Long concertId, Long dateId, Long seatId, Integer total, PaymentStatus status) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.concertId = concertId;
        this.dateId = dateId;
        this.seatId = seatId;
        this.total = total;
        this.status = status;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
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

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}
