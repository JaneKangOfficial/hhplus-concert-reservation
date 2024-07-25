package io.hhplus.concert.domain.points.infrastructure.repositoryImpl;

import io.hhplus.concert.domain.points.business.repository.PointsRepository;
import io.hhplus.concert.domain.points.infrastructure.entity.PointHistoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PointsRepositoryImpl implements PointsRepository {
    private final PointsJpaRepository pointsJpaRepository;

    @Override
    public PointHistoryEntity save(PointHistoryEntity pointHistoryEntity) {
        return pointsJpaRepository.save(pointHistoryEntity);
    }

    @Override
    public Optional<PointHistoryEntity> findById(Long id) {
        return pointsJpaRepository.findById(id);
    }

}
