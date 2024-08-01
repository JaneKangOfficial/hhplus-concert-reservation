package io.hhplus.concert.concert;

import io.hhplus.concert.domain.concerts.business.service.ConcertsService;
import io.hhplus.concert.points.PointsConcurrencyLockTest;
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

@SpringBootTest
public class ConcertsCacheTest {

    private static final Logger log = LoggerFactory.getLogger(PointsConcurrencyLockTest.class);

    @Autowired
    private ConcertsService concertsService;

    @Test
    @DisplayName("콘서트 목록 조회 캐시 테스트")
    public void ConcersCacheTest() throws InterruptedException {
        Long startTime = System.currentTimeMillis();
        Integer threadCount = 100;

        CountDownLatch latch = new CountDownLatch(threadCount);  // 모든 스레드가 완료될 때까지 기다림
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);    // 멀티스레드 생성

        // 동시성 이슈 테스트
        IntStream.range(0, threadCount).forEach(i -> {
            executor.submit(() -> {
                try {
                    log.info("Thread {} started", Thread.currentThread().getName());
                    concertsService.getConcerts();    // service 호출
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

        Long endTime = System.currentTimeMillis();
        log.info("테스트 소요 시간: {}", (endTime - startTime) + "ms");

    }

}
