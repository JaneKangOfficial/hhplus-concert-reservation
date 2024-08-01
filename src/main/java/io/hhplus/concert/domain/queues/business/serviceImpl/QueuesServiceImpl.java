package io.hhplus.concert.domain.queues.business.serviceImpl;

import io.hhplus.concert.domain.queues.business.service.QueuesService;
import io.hhplus.concert.domain.queues.infrastructure.QueuesRedisRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.resps.Tuple;

import java.util.List;

@Service
public class QueuesServiceImpl implements QueuesService {

    private static final String WAIT_QUEUES = "waitQueues";
    private static final String ACTIVE_QUEUES = "activeQueues";

    private static final Logger log = LoggerFactory.getLogger(QueuesServiceImpl.class);

    private final QueuesRedisRepositoryImpl queuesRedisRepository;

    public QueuesServiceImpl(QueuesRedisRepositoryImpl queuesRedisRepository) {
        this.queuesRedisRepository = queuesRedisRepository;
    }

    // repository에 작성하고 service 호출해서 사용하기

    // 대기열 진입
    @Override
    @Transactional
    public Long entryWaitingQueue(Long userId) {

        // 대기열에 있는가? 순서 확인
        Long rank = queuesRedisRepository.getRank(userId, WAIT_QUEUES);
        if (rank == null) {

            queuesRedisRepository.addQueue(userId, WAIT_QUEUES);    // 없다면 대기열 진입 (있는데 또 넣으면 새로고침)
            queuesRedisRepository.expireQueue(WAIT_QUEUES, 60 * 30);  // TTL 설정 30분
            rank = queuesRedisRepository.getRank(userId, WAIT_QUEUES);
        }

        // 몇등인지 return
        return rank + 1;
    }

    // 활성 큐 진입
    @Override
    public void entryActiveQueue() {
        List<Tuple> rangeList = queuesRedisRepository.getRangeWithScores(WAIT_QUEUES, 0L, 49L); // 50명씩 가져오기

        rangeList.stream().forEach(r -> {
            queuesRedisRepository.delQueue(Long.valueOf(r.getElement()), WAIT_QUEUES); // 대기열 삭제
            queuesRedisRepository.addQueue(Long.valueOf(r.getElement()), ACTIVE_QUEUES + ":" + r.getElement()); // 활성으로 진입 / keys ACTIVE_QUEUES:* 명령어 치면 풀스캔 (* 운영에서 하면 안된다! 레디스는 싱글 스레드이다!)
            queuesRedisRepository.expireQueue(ACTIVE_QUEUES + ":" + r.getElement(), 60 * 5);  // TTL 설정 5분
        });
    }

    // 활성 큐 만료 -> TTL 로 변경
/*    @Override
    public void expiredQueue() {
        List<Tuple> rangeList = queuesRedisRepository.getRangeWithScores(ACTIVE_QUEUES, 0L, 49L); // 50명씩 가져오기
        Long currentTimeMillis = System.currentTimeMillis();    // 1000 = 1초 / 1000 * 60 = 1분

        rangeList.stream().forEach(s -> {
            Long score = (long) s.getScore();
            if (currentTimeMillis - score > 1800000) {    // 30분

                // 만료 시간이 넘었으면 삭제 => 보다는 TTL을 줄여서 지우자
                queuesRedisRepository.delQueue(Long.valueOf(s.getElement()), ACTIVE_QUEUES);

            }
        });
    }*/

}
