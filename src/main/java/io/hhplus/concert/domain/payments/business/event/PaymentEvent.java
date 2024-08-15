package io.hhplus.concert.domain.payments.business.event;

import io.hhplus.concert.domain.payments.infrastructure.entity.PaymentsHistoryEntity;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentEvent {

    private final Long paymentId;

    private final Long userId;

    private final String concertTitle;

    private final Long concertPrice;

    private final LocalDate concertDate;

    private final Long concertSeatNum;

    public PaymentEvent(Long paymentId, Long userId, String concertTitle, Long concertPrice, LocalDate concertDate, Long concertSeatNum) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.concertTitle = concertTitle;
        this.concertPrice = concertPrice;
        this.concertDate = concertDate;
        this.concertSeatNum = concertSeatNum;
    }

    public static PaymentsHistoryEntity convertToEntity(PaymentEvent paymentEvent) {
        return new PaymentsHistoryEntity(paymentEvent.getPaymentId(), paymentEvent.getUserId(), paymentEvent.getConcertTitle(), paymentEvent.getConcertPrice(), paymentEvent.getConcertDate(), paymentEvent.getConcertSeatNum());
    }
}
