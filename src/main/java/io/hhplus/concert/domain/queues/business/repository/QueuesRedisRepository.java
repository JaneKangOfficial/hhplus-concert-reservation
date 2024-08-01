package io.hhplus.concert.domain.queues.business.repository;

import redis.clients.jedis.resps.Tuple;

import java.util.List;

public interface QueuesRedisRepository {

    void addQueue(Long userId, String key);

    void expireQueue(String key, int ttl);

    Long getRank(Long userId, String key);

    List<String> getRange(String key, Long start, Long stop);

    List<Tuple> getRangeWithScores(String key, Long start, Long stop);

    Long delQueue(Long userId, String key);

}
