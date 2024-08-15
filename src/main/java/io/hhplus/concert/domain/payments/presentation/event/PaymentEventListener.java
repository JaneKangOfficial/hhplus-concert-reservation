package io.hhplus.concert.domain.payments.presentation.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hhplus.concert.domain.payments.business.event.PaymentEvent;
import io.hhplus.concert.domain.payments.business.message.PaymentMessageOutboxWriter;
import io.hhplus.concert.domain.payments.business.message.PaymentMessageSender;
import io.hhplus.concert.domain.payments.business.service.PaymentsService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

// 이벤트 구독 서비스
@Component
public class PaymentEventListener {
    private final PaymentsService paymentsService;
    private final PaymentMessageOutboxWriter paymentMessageOutboxWriter;
    private final ObjectMapper objectMapper;
    private final PaymentMessageSender paymentMessageSender;

    public PaymentEventListener(PaymentsService paymentsService, PaymentMessageOutboxWriter paymentMessageOutboxWriter, ObjectMapper objectMapper, PaymentMessageSender paymentMessageSender) {
        this.paymentsService = paymentsService;
        this.paymentMessageOutboxWriter = paymentMessageOutboxWriter;
        this.objectMapper = objectMapper;
        this.paymentMessageSender = paymentMessageSender;
    }

    // 비동기로 주체의 트랜잭션이 커밋된 후에 수행
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    // AFTER_COMMIT : 핵심 로직과 부과 로직의 분리 (히스토리 저장에 실패했다고 결제가 롤백되면 안되니까 핵심 로직 커밋 후에 히스토리 저장)
    // TransactionalEventListener : PaymentEvent 객체에 이벤트가 발행되면 호출된다
    public void savePaymentHistoryHandler(PaymentEvent paymentEvent) {
        // 결제 히스토리 저장
        paymentsService.savePaymentHistory(paymentEvent);
    }

}
