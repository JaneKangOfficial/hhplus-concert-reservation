package io.hhplus.concert.domain.queues.business.service;

public interface QueuesService {

    Long enqueueWaitUsers(Long userId);

    void enqueueActiveUsers();

//    void expiredQueue();


}
