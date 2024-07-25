package io.hhplus.concert.points;

import io.hhplus.concert.common.status.PointsType;
import io.hhplus.concert.domain.concerts.business.repository.UsersRepository;
import io.hhplus.concert.domain.concerts.infrastructure.entity.UsersEntity;
import io.hhplus.concert.domain.points.business.repository.PointsRepository;
import io.hhplus.concert.domain.points.business.service.PointsService;
import io.hhplus.concert.domain.points.presentation.dto.request.PointsRequestDTO;
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
public class PointsConcurrencyLockTest {

    private static final Logger log = LoggerFactory.getLogger(PointsConcurrencyLockTest.class);

    @Autowired
    private PointsRepository pointsRepository;

    @Autowired
    private PointsService pointsService;

    @Autowired
    private UsersRepository usersRepository;

    /**
     * 낙관적 락 테스트
     * UsersEntity에 @Version 추가
     **/

    /**
     * 비관적 락 테스트
     * Select에 @ Lock(LockModeType.PESSIMISTIC_WRITE) 추가
     **/

    @Test
    @DisplayName("포인트 100명 동시성 락 테스트")
    public void PointsLockTest() throws InterruptedException {
        Long startTime = System.currentTimeMillis();
        Long userId = 1L;
        Integer threadCount = 100;

        PointsRequestDTO pointsRequestDTO = new PointsRequestDTO(userId, 100L, PointsType.CHARGE);

        CountDownLatch latch = new CountDownLatch(threadCount);  // 모든 스레드가 완료될 때까지 기다림
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);    // 멀티스레드 생성

        // 동시성 이슈 테스트
        IntStream.range(0, threadCount).forEach(i -> {
            executor.submit(() -> {
                try {
                    log.info("Thread {} started", Thread.currentThread().getName());
                    pointsRequestDTO.setPoint(pointsRequestDTO.getPoint() + 10L);
                    pointsService.chargePoint(pointsRequestDTO);    // service 호출
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

        UsersEntity updatedUser = usersRepository.findByUserId(userId).orElseThrow();
        log.info("{} Thread Test Final points: {}", Thread.currentThread().getName(), updatedUser.getPoint());

        Long endTime = System.currentTimeMillis();
        log.info("테스트 소요 시간: {}", (endTime - startTime) + "ms");

    }


}
