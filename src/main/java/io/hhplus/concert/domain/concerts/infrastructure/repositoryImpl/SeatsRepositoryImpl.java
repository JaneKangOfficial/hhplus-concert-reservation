package io.hhplus.concert.domain.concerts.infrastructure.repositoryImpl;

import io.hhplus.concert.common.status.SeatsStatus;
import io.hhplus.concert.domain.concerts.business.repository.SeatsRepository;
import io.hhplus.concert.domain.concerts.infrastructure.entity.SeatsEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class SeatsRepositoryImpl implements SeatsRepository {

    private final SeatsJpaRepository seatsJpaRepository;

    public SeatsRepositoryImpl(SeatsJpaRepository seatsJpaRepository) {
        this.seatsJpaRepository = seatsJpaRepository;
    }

    @Override
    public List<SeatsEntity> findAll() {
        return seatsJpaRepository.findAll();
    }

    @Override
    public void save(SeatsEntity seat) {
        seatsJpaRepository.save(seat);
    }

    @Override
    public Optional<SeatsEntity> findById(Long seatId) {
        return seatsJpaRepository.findById(seatId);
    }

    @Override
    public List<Long> findSeatNumbersByConcertIdAndDateId(Long concertId, Long dateId) {
        return seatsJpaRepository.findSeatNumbersByConcertIdAndDateId(concertId, dateId);
    }

    @Override
    public List<SeatsEntity> findAllByStatus(SeatsStatus seatsStatus) {
        return seatsJpaRepository.findAllByStatus(seatsStatus);
    }

    @Override
    public void updateStatusAndLockuntil(Long seatId, SeatsStatus seatsStatus, LocalDateTime localDateTime) {
        seatsJpaRepository.updateStatusAndLockuntil(seatId, seatsStatus, localDateTime);
    }

}
