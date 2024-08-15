package io.hhplus.concert.domain.payments.presentation.scheduler;

import io.hhplus.concert.domain.payments.business.service.PaymentsService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PaymentsScheduler {

    private final PaymentsService paymentsService;

    public PaymentsScheduler(PaymentsService paymentsService) {
        this.paymentsService = paymentsService;
    }

    @Scheduled(cron = "5 * * * * *")
    public void resendKafka() {
        paymentsService.resendKafka();
    }

}
