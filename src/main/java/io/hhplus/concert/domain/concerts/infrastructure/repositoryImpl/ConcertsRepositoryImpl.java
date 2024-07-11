package io.hhplus.concert.domain.concerts.infrastructure.repositoryImpl;

import io.hhplus.concert.domain.concerts.business.repository.ConcertsRepository;
import io.hhplus.concert.domain.concerts.infrastructure.entity.DatesEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//@RequiredArgsConstructor // 생성자 자동 생성
public class ConcertsRepositoryImpl implements ConcertsRepository {

    private final DatesJpaRepository datesJpaRepository;

    private final SeatsJpaRepository seatsJpaRepository;

    public ConcertsRepositoryImpl(DatesJpaRepository datesJpaRepository, SeatsJpaRepository seatsJpaRepository) {
        this.datesJpaRepository = datesJpaRepository;
        this.seatsJpaRepository = seatsJpaRepository;
    }

    @Override
    public List<DatesEntity> findByConcertId(Long concertId) {
        return datesJpaRepository.findByConcertId(concertId);
    }

    @Override
    public List<Long> findSeatNumbersByConcertIdAndDateId(Long concertId, Long dateId) {
        return seatsJpaRepository.findSeatNumbersByConcertIdAndDateId(concertId, dateId);
    }
}
