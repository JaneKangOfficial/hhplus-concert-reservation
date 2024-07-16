package io.hhplus.concert.domain.payments.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.hhplus.concert.domain.payments.business.entity.PaymentsEntity;
import io.hhplus.concert.domain.payments.business.entity.PaymentsStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentsResponseDTO {

    private Long paymentId;

    private Long userId;

    private Long concertId;

    private Long dateId;

    private Long seatId;

    private Long total;

    private PaymentsStatus status;

    public PaymentsResponseDTO(Long paymentId, Long userId, Long total, PaymentsStatus status) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.total = total;
        this.status = status;
    }

    public static PaymentsResponseDTO convertToDTO(PaymentsEntity paymentsEntity) {
        return new PaymentsResponseDTO(paymentsEntity.getId(), paymentsEntity.getUserId(), paymentsEntity.getTotal(), paymentsEntity.getStatus());
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

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public PaymentsStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentsStatus status) {
        this.status = status;
    }
}
