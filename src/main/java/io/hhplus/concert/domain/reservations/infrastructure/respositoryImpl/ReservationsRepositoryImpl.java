package io.hhplus.concert.domain.reservations.infrastructure.respositoryImpl;

import io.hhplus.concert.domain.reservations.business.repository.ReservationsRepository;
import io.hhplus.concert.domain.reservations.infrastructure.entity.ReservationsEntity;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationsRepositoryImpl implements ReservationsRepository {

    private final ReservationsJpaRepository reservationsJpaRepository;

    public ReservationsRepositoryImpl(ReservationsJpaRepository reservationsJpaRepository) {
        this.reservationsJpaRepository = reservationsJpaRepository;
    }


    @Override
    public ReservationsEntity save(ReservationsEntity reservationsEntity) {
        return reservationsJpaRepository.save(reservationsEntity);
    }

}
