package io.hhplus.concert.domain.payments.infrastructure.event;

import io.hhplus.concert.domain.payments.business.event.PaymentEvent;
import io.hhplus.concert.domain.payments.business.event.PaymentEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

// 이벤트 발행 서비스
@Component
public class PaymentCoreEventPublisher implements PaymentEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public PaymentCoreEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void savePaymentHistory(PaymentEvent paymentEvent) {
        applicationEventPublisher.publishEvent(paymentEvent);
    }

}
