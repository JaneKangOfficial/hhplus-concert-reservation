package io.hhplus.concert.domain.concerts.infrastructure.repositoryImpl;

import io.hhplus.concert.domain.concerts.infrastructure.entity.DatesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface DatesJpaRepository extends JpaRepository<DatesEntity, Long> {

    List<DatesEntity> findByConcertId(Long concertId);
}
