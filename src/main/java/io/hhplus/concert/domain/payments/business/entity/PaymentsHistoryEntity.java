package io.hhplus.concert.domain.payments.business.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_history")
@NoArgsConstructor
@Data
public class PaymentsHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long paymentId;

    private Long userId;

    private String concertTitle;

    private Long concertPrice;

    private LocalDate concertDate;

    private Long concertSeatNum;

    private LocalDateTime createdAt;

    @Builder
    public PaymentsHistoryEntity(Long paymentId, Long userId, String concertTitle, Long concertPrice, LocalDate concertDate, Long concertSeatNum) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.concertTitle = concertTitle;
        this.concertPrice = concertPrice;
        this.concertDate = concertDate;
        this.concertSeatNum = concertSeatNum;
        this.createdAt = LocalDateTime.now();
    }

}
