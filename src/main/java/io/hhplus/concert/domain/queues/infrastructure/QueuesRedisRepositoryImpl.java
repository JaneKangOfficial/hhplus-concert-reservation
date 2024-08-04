package io.hhplus.concert.domain.queues.infrastructure;

import io.hhplus.concert.domain.queues.business.repository.QueueRepository;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.resps.Tuple;

import java.util.List;

@Repository
public class QueuesRedisRepositoryImpl implements QueueRepository {

    private final Jedis jedis;

    public QueuesRedisRepositoryImpl(Jedis jedis) {
        this.jedis = jedis;
    }

    @Override
    public void addQueue(Long userId, String key) {
        jedis.zadd(key, System.currentTimeMillis(), userId.toString());
    }

    @Override
    public void expireQueue(String key, int ttl) {
        // 30 * 60 = 30분
        jedis.expire(key, ttl);
    }


    // 난 몇등인가?
    @Override
//    @Cacheable()
    public Long getRank(Long userId, String key) {
        return jedis.zrank(key, userId.toString());
    }

    // 범위만큼 대기열 목록 가져오기
    @Override
    public List<String> getRange(String key, Long start, Long stop) {
        return jedis.zrange(key, start, stop);
    }

    // 범위만큼 대기열 목록과 Score 가져오기
    @Override
    public List<Tuple> getRangeWithScores(String key, Long start, Long stop) {
        return jedis.zrangeWithScores(key, start, stop);
    }

    // 삭제하기
    @Override
    public Long delQueue(Long userId, String key) {
        return jedis.zrem(key, userId.toString());
    }


}
