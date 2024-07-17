package io.hhplus.concert.domain.reservations.infrastructure.respositoryImpl;

import io.hhplus.concert.domain.reservations.infrastructure.entity.ReservationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ReservationsJpaRepository extends JpaRepository<ReservationsEntity, Long> {


}
