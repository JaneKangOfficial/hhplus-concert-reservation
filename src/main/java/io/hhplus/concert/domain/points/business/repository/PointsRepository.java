package io.hhplus.concert.domain.points.business.repository;

import io.hhplus.concert.domain.points.infrastructure.entity.PointHistoryEntity;

import java.util.Optional;

public interface PointsRepository {

    PointHistoryEntity save(PointHistoryEntity pointHistoryEntity);

    Optional<PointHistoryEntity> findById(Long id);

}
