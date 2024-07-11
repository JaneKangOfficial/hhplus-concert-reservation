package io.hhplus.concert.domain.concerts.infrastructure.repositoryImpl;

import io.hhplus.concert.domain.concerts.infrastructure.entity.SeatsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface SeatsJpaRepository extends JpaRepository<SeatsEntity, Long> {

    @Query(value = "SELECT s.num " +
            "FROM seat s " +
            "JOIN concert_option co ON s.concert_option_id = co.id " +
            "WHERE co.concert_id = :concertId AND co.id = :dateId AND s.status = 'AVAILABLE'", nativeQuery = true)
    List<Long> findSeatNumbersByConcertIdAndDateId(Long concertId, Long dateId);
    // nativeQuery 는 반환 타입을 직접 지정할 수가 없다... List<SeatsEntity> 로 불가, num의 타입인 Long 으로 반환!
}
