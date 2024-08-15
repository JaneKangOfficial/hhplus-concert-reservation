package io.hhplus.concert.domain.payments.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.hhplus.concert.common.status.PaymentsStatus;
import io.hhplus.concert.domain.payments.infrastructure.entity.PaymentsEntity;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
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

}
