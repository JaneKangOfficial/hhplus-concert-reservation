package io.hhplus.concert.domain.reservations.business.repository;

import io.hhplus.concert.domain.reservations.infrastructure.entity.ReservationsEntity;

public interface ReservationsRepository {

    ReservationsEntity save(ReservationsEntity reservationsEntity);
}
