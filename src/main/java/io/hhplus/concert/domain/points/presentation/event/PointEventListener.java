package io.hhplus.concert.domain.points.presentation.event;

import io.hhplus.concert.domain.payments.business.service.PaymentsService;
import io.hhplus.concert.domain.points.infrastructure.entity.PointHistoryEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

// 이벤트 구독 서비스
@Component
public class PointEventListener {
    private final PaymentsService paymentsService;

    public PointEventListener(PaymentsService paymentsService) {
        this.paymentsService = paymentsService;
    }

    // 비동기로 주체의 트랜잭션이 커밋된 후에 수행
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void savePointHistoryHandler(PointHistoryEntity pointHistoryEntity) {
        // 포인트 히스토리 저장
        paymentsService.calculatePoint(pointHistoryEntity);
    }
}
