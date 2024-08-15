package io.hhplus.concert.domain.payments.business.event;

public interface PaymentEventPublisher {
    void savePaymentHistory(PaymentEvent paymentEvent);
}
