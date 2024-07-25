package io.hhplus.concert.domain.concerts.business.repository;

import io.hhplus.concert.common.status.SeatsStatus;
import io.hhplus.concert.domain.concerts.infrastructure.entity.SeatsEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SeatsRepository {
    List<SeatsEntity> findAll();

    void save(SeatsEntity seat);

    // 비관적 락
//    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<SeatsEntity> findById(Long seatId);

    List<Long> findSeatNumbersByConcertIdAndDateId(Long concertId, Long dateId);

    List<SeatsEntity> findAllByStatus(SeatsStatus seatsStatus);

    void updateStatusAndLockuntil(Long seatId, SeatsStatus seatsStatus, LocalDateTime localDateTime);

    void updateStatusAndLockuntilWithLock(SeatsEntity seatsEntity);
}

