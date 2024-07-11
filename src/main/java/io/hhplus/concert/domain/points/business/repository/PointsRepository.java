package io.hhplus.concert.domain.points.business.repository;

import io.hhplus.concert.domain.points.infrastructure.entity.PointHistoryEntity;

public interface PointsRepository {
    void save(PointHistoryEntity pointHistoryEntity);
}
