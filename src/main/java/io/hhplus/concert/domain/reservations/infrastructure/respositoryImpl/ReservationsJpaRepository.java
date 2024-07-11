package io.hhplus.concert.domain.reservations.infrastructure.respositoryImpl;

import io.hhplus.concert.domain.reservations.infrastructure.entity.ReservationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface ReservationsJpaRepository extends JpaRepository<ReservationsEntity, Long> {

    @Query(value = "SELECT sr.id, sr.user_id, co.concert_id, co.date_id, s.seat_id, sr.status " +
            "FROM concert_option co " +
            "JOIN seat s ON s.concert_option_id = co.date_id " +
            "JOIN seat_reservation sr ON sr.seat_id = s.seat_id " +
            "WHERE sr.user_id = :userId AND co.concert_id = :concertId AND co.date_id = :dateId AND sr.seat_id = :seatId AND s.status = 'AVAILABLE'", nativeQuery = true)
    Optional<ReservationsEntity> findByUserIdAndConcertIdAndDateIdAndSeatId(Long userId, Long concertId, Long dateId, Long seatId);

}
