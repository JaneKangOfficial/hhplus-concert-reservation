package io.hhplus.concert.domain.points.business.event;

import io.hhplus.concert.domain.points.infrastructure.entity.PointHistoryEntity;

public interface PointEventPublisher {
    void savePointHistory(PointHistoryEntity pointHistoryEntity);
}
