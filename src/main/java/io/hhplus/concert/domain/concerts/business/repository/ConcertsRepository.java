package io.hhplus.concert.domain.concerts.business.repository;

import io.hhplus.concert.domain.concerts.infrastructure.entity.ConcertEntity;
import io.hhplus.concert.domain.concerts.infrastructure.entity.DatesEntity;

import java.util.List;
import java.util.Optional;

public interface ConcertsRepository {
    List<DatesEntity> findByConcertId(Long concertId);

    List<Long> findSeatNumbersByConcertIdAndDateId(Long concertId, Long dateId);

    Optional<ConcertEntity> findById(Long concertId);

    List<ConcertEntity> findAll();
}
