package io.hhplus.concert.domain.points.infrastructure.repositoryImpl;

import io.hhplus.concert.domain.points.infrastructure.entity.PointHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointsJpaRepository extends JpaRepository<PointHistoryEntity, Long> {
}
