package io.hhplus.concert.domain.queues.presentation.controller;

import io.hhplus.concert.domain.queues.business.service.QueuesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/queues")
public class QueuesController {

    private final QueuesService queuesService;

    public QueuesController(QueuesService queuesService) {
        this.queuesService = queuesService;
    }

    @GetMapping("/enqueueWaitUsers")
    public Long enqueueWaitUsers(@RequestParam Long userId) {
        return queuesService.enqueueWaitUsers(userId);
    }

    // 테스트를 위한 호출
    @GetMapping("/enqueueActiveUsers")
    public void enqueueActiveUsers() {
        queuesService.enqueueActiveUsers();
    }

    // 테스트를 위한 호출
/*    @GetMapping("/expiredQueue")
    public void expiredQueue() {
        queuesService.expiredQueue();
    }*/

}
