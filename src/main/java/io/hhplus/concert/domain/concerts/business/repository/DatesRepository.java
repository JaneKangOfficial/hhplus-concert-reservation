package io.hhplus.concert.domain.concerts.business.repository;

import io.hhplus.concert.domain.concerts.infrastructure.entity.DatesEntity;

import java.util.List;
import java.util.Optional;

public interface DatesRepository {
    List<DatesEntity> findByConcertId(Long concertId);

    Optional<DatesEntity> findById(Long dateId);
}
