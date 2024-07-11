package io.hhplus.concert.domain.points.infrastructure.repositoryImpl;

import io.hhplus.concert.domain.points.business.repository.PointsRepository;
import io.hhplus.concert.domain.points.infrastructure.entity.PointHistoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PointsRepositoryImpl implements PointsRepository {
    private final PointsJpaRepository pointsJpaRepository;

    @Override
    public void save(PointHistoryEntity pointHistoryEntity) {
        pointsJpaRepository.save(pointHistoryEntity);
    }
}
