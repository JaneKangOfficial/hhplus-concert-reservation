package io.hhplus.concert.config;

import io.hhplus.concert.common.status.SeatReservationsStatus;
import io.hhplus.concert.common.status.SeatsStatus;
import io.hhplus.concert.domain.concerts.infrastructure.entity.ConcertEntity;
import io.hhplus.concert.domain.concerts.infrastructure.entity.DatesEntity;
import io.hhplus.concert.domain.concerts.infrastructure.entity.SeatsEntity;
import io.hhplus.concert.domain.concerts.infrastructure.entity.UsersEntity;
import io.hhplus.concert.domain.concerts.infrastructure.repositoryImpl.ConcertJpaRepository;
import io.hhplus.concert.domain.concerts.infrastructure.repositoryImpl.DatesJpaRepository;
import io.hhplus.concert.domain.concerts.infrastructure.repositoryImpl.SeatsJpaRepository;
import io.hhplus.concert.domain.concerts.infrastructure.repositoryImpl.UsersJpaRepository;
import io.hhplus.concert.domain.tokens.infrastructure.entity.TokensEntity;
import io.hhplus.concert.domain.tokens.infrastructure.repositoryImpl.TokensJpaRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class InitData implements ApplicationRunner {

    private final TokensJpaRepository tokensJpaRepository;
    private final UsersJpaRepository usersJpaRepository;
    private final ConcertJpaRepository concertJpaRepository;
    private final DatesJpaRepository datesJpaRepository;
    private final SeatsJpaRepository seatsJpaRepository;

    public InitData(TokensJpaRepository tokensJpaRepository, UsersJpaRepository usersJpaRepository
            , ConcertJpaRepository concertJpaRepository, DatesJpaRepository datesJpaRepository, SeatsJpaRepository seatsJpaRepository) {
        this.tokensJpaRepository = tokensJpaRepository;
        this.usersJpaRepository = usersJpaRepository;
        this.concertJpaRepository = concertJpaRepository;
        this.datesJpaRepository = datesJpaRepository;
        this.seatsJpaRepository = seatsJpaRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 초기 데이터 생성 - 토큰 테이블
        TokensEntity tokensEntity1 = new TokensEntity();
        tokensEntity1.setUserId(1L);
        tokensEntity1.setToken("2a9ec704-75b9-4993-aaf5-5a80cfc3de04");
        tokensEntity1.setStatus(SeatReservationsStatus.TokensStatus.ACTIVE);
        tokensEntity1.setCreatedAt(LocalDateTime.now());
        tokensEntity1.setExpirationAt(LocalDateTime.now().plusMinutes(5));
        tokensJpaRepository.save(tokensEntity1);

        TokensEntity tokensEntity2 = new TokensEntity();
        tokensEntity2.setUserId(2L);
        tokensEntity2.setToken("de8482a6-82fd-484f-8010-566e7a27e74d");
        tokensEntity2.setStatus(SeatReservationsStatus.TokensStatus.EXPIRATION);
        tokensEntity2.setCreatedAt(LocalDateTime.now());
        tokensEntity2.setExpirationAt(LocalDateTime.now().minusMinutes(5)); // 테스트를 위한 마이너스 만료 시간으로 설정
        tokensJpaRepository.save(tokensEntity2);

        // 초기 데이터 생성 - 사용자 테이블
        UsersEntity usersEntity1 = new UsersEntity();
        usersEntity1.setUserId(1L);
        usersEntity1.setPoint(50000L);
        usersJpaRepository.save(usersEntity1);

        UsersEntity usersEntity2 = new UsersEntity();
        usersEntity2.setUserId(2L);
        usersEntity2.setPoint(20000L);
        usersJpaRepository.save(usersEntity2);

        // 초기 데이터 생성 - 콘서트 테이블
        ConcertEntity concertEntity1 = new ConcertEntity();
        concertEntity1.setTitle("A 콘서트");
        concertEntity1.setPrice(10000L);
        concertJpaRepository.save(concertEntity1);

        ConcertEntity concertEntity2 = new ConcertEntity();
        concertEntity2.setTitle("B 콘서트");
        concertEntity2.setPrice(20000L);
        concertJpaRepository.save(concertEntity2);

        // 초기 데이터 생성 - 콘서트 옵션 테이블
        DatesEntity datesEntity1 = new DatesEntity();
        datesEntity1.setConcertOptionId(1L);
        datesEntity1.setConcertId(1L);
        datesEntity1.setConcertDate(LocalDate.parse("2024-07-10"));
        datesJpaRepository.save(datesEntity1);

        DatesEntity datesEntity2 = new DatesEntity();
        datesEntity2.setConcertOptionId(2L);
        datesEntity2.setConcertId(1L);
        datesEntity2.setConcertDate(LocalDate.parse("2024-07-20"));
        datesJpaRepository.save(datesEntity2);

        // 초기 데이터 생성 - 좌석 테이블
        DatesEntity datesEntity = datesJpaRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("DatesEntity not found"));

        SeatsEntity seatsEntity1 = new SeatsEntity();
        seatsEntity1.setConcertOption(datesEntity);
        seatsEntity1.setNum(1001L);
        seatsEntity1.setStatus(SeatsStatus.AVAILABLE);
        seatsEntity1.setLockUntil(LocalDateTime.now().plusMinutes(5));
        seatsJpaRepository.save(seatsEntity1);

        SeatsEntity seatsEntity2 = new SeatsEntity();
        seatsEntity2.setConcertOption(datesEntity);
        seatsEntity2.setNum(1101L);
        seatsEntity2.setStatus(SeatsStatus.UNAVAILABLE);
        seatsEntity2.setLockUntil(LocalDateTime.now().minusMinutes(5)); // 테스트를 위한 마이너스 만료 시간으로 설정
        seatsJpaRepository.save(seatsEntity2);

    }


}