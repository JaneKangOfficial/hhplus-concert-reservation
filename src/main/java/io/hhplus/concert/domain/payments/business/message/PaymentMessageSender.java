package io.hhplus.concert.domain.payments.business.message;

import io.hhplus.concert.domain.payments.business.event.PaymentEvent;

public interface PaymentMessageSender {
    void send(PaymentEvent paymentEvent);
}
