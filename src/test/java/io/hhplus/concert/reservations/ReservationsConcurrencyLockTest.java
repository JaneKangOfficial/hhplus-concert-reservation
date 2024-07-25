package io.hhplus.concert.reservations;

import io.hhplus.concert.common.status.ReservationsStatus;
import io.hhplus.concert.domain.concerts.business.service.ConcertsService;
import io.hhplus.concert.domain.reservations.business.repository.ReservationsRepository;
import io.hhplus.concert.domain.reservations.presentation.dto.request.ReservationsRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@SpringBootTest     // 실제 DB에서 확인해야 하기 때문에 @Mock 보다는 @SpringBootTest를 사용
public class ReservationsConcurrencyLockTest {

    private static final Logger log = LoggerFactory.getLogger(ReservationsConcurrencyLockTest.class);

    @Autowired
    private ConcertsService concertsService;

    @Autowired
    private ReservationsRepository reservationsRepository;

    /**
     * 낙관적 락 테스트
     * UsersEntity에 @Version 추가
     **/

    /**
     * 비관적 락 테스트
     * Select에 @ Lock(LockModeType.PESSIMISTIC_WRITE) 추가
     **/

    @Test
    @DisplayName("좌석 예약 100명 동시성 락 테스트")
    public void ReservationLockTest() throws InterruptedException {
        Long startTime = System.currentTimeMillis();
        Long concertId = 1L;
        Long dateId = 1L;
        Long seatId = 1L;
        Integer threadCount = 100;

        CountDownLatch latch = new CountDownLatch(threadCount);  // 모든 스레드가 완료될 때까지 기다림
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);    // 멀티스레드 생성

        // 동시성 이슈 테스트
        IntStream.range(0, threadCount).forEach(i -> {
            executor.submit(() -> {
                try {
                    Long userId = (long) (i + 1);  // userId를 1부터 10까지 설정
                    ReservationsRequestDTO reservationsRequestDTO = new ReservationsRequestDTO(userId, concertId, dateId, seatId, ReservationsStatus.APPLY);

                    log.info("Thread {} started", Thread.currentThread().getName());
                    concertsService.reservations(reservationsRequestDTO);    // service 호출
                    log.info("Thread {} finished", Thread.currentThread().getName());
                } catch (Exception e) {
                    log.info("Thread {} exception: {}", Thread.currentThread().getName(), e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        });

        latch.await();
        executor.shutdown();

//        ReservationsEntity reservationsEntity = reservationsRepository.findByConcertIdAndDateIdAndSeatId(concertId, dateId, seatId).orElseThrow();
//        log.info("{} Thread Test Final reservationsEntity: {}", Thread.currentThread().getName(), reservationsEntity);

        Long endTime = System.currentTimeMillis();
        log.info("테스트 소요 시간: {}", (endTime - startTime) + "ms");

    }


}
