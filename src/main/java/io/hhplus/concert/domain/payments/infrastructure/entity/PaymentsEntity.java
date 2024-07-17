package io.hhplus.concert.domain.payments.infrastructure.entity;

import io.hhplus.concert.common.status.PaymentsStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payment")
@NoArgsConstructor
@Data
public class PaymentsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long reservationId;

    private Long userId;

    private Long total;

    private PaymentsStatus status;

    public PaymentsEntity(Long reservationId, Long userId, Long total, PaymentsStatus status) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.total = total;
        this.status = status;
    }
}
