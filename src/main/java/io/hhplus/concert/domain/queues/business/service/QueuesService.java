package io.hhplus.concert.domain.queues.business.service;

public interface QueuesService {

    Long entryWaitingQueue(Long userId);

    void enqueueActiveUsers();

//    void expiredQueue();


}
