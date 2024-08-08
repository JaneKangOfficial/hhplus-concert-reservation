package io.hhplus.concert.domain.points.infrastructure.event;

import io.hhplus.concert.domain.points.business.event.PointEventPublisher;
import io.hhplus.concert.domain.points.infrastructure.entity.PointHistoryEntity;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

// 이벤트 발행 서비스
@Component
public class PointCoreEventPublisher implements PointEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public PointCoreEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void savePointHistory(PointHistoryEntity pointHistoryEntity) {
        applicationEventPublisher.publishEvent(pointHistoryEntity);
    }
}
