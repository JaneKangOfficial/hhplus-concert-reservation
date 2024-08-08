package io.hhplus.concert.concert;

import io.hhplus.concert.domain.concerts.business.service.ConcertsService;
import io.hhplus.concert.points.PointsConcurrencyLockTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConcertIndexTest {

    private static final Logger log = LoggerFactory.getLogger(PointsConcurrencyLockTest.class);

    @Autowired
    private ConcertsService concertsService;

    @Test
    @DisplayName("좌석 조회 인덱스 테스트")
    public void ConcersCacheTest() {
        Long startTime = System.currentTimeMillis();

        try {
            log.info("Thread {} started", Thread.currentThread().getName());
            concertsService.getSeats(90000L, 90000L, 1L);    // service 호출
            log.info("Thread {} finished", Thread.currentThread().getName());
        } catch (Exception e) {
            log.info("Thread {} exception: {}", Thread.currentThread().getName(), e.getMessage());
        }

        Long endTime = System.currentTimeMillis();
        log.info("테스트 소요 시간: {}", (endTime - startTime) + "ms");

    }

}
