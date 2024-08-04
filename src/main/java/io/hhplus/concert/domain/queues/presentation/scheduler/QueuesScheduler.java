package io.hhplus.concert.domain.queues.presentation.scheduler;

import io.hhplus.concert.domain.queues.business.service.QueuesService;
import io.hhplus.concert.domain.queues.business.serviceImpl.QueuesServiceImpl;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class QueuesScheduler {

    private static final Logger log = LoggerFactory.getLogger(QueuesServiceImpl.class);

    private final QueuesService queuesService;

    public QueuesScheduler(QueuesService queuesService) {
        this.queuesService = queuesService;
    }

    @Scheduled(cron = "*/30 * * * * *")   // 30초 마다 호출
    @Transactional
    public void enqueueActiveUsers() {
        log.info("============= enqueueActiveUsers START =============");
        queuesService.enqueueActiveUsers();
        log.info("============= enqueueActiveUsers END =============");
    }

//    @Scheduled(cron = "* */30 * * * *")   // 30분 마다 호출
//    @Transactional
//    public void expiredQueue() {
//        log.info("============= expiredQueue START =============");
//        queuesService.expiredQueue();
//        log.info("============= expiredQueue END =============");
//    }

}
