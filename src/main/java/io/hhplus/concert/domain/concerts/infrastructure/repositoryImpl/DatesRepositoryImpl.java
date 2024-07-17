package io.hhplus.concert.domain.concerts.infrastructure.repositoryImpl;

import io.hhplus.concert.domain.concerts.business.repository.DatesRepository;
import io.hhplus.concert.domain.concerts.infrastructure.entity.DatesEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DatesRepositoryImpl implements DatesRepository {

    private final DatesJpaRepository datesJpaRepository;

    public DatesRepositoryImpl(DatesJpaRepository datesJpaRepository) {
        this.datesJpaRepository = datesJpaRepository;
    }

    @Override
    public List<DatesEntity> findByConcertId(Long concertId) {
        return datesJpaRepository.findByConcertId(concertId);
    }

    @Override
    public Optional<DatesEntity> findById(Long dateId) {
        return datesJpaRepository.findById(dateId);
    }
}
