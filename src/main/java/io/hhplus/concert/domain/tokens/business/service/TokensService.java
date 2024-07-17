package io.hhplus.concert.domain.tokens.business.service;

import io.hhplus.concert.domain.tokens.presentation.dto.response.TokensResponseDTO;
import org.springframework.scheduling.annotation.Scheduled;

public interface TokensService {

    void requestTokenIssuance(Long userId);

    TokensResponseDTO issueToken(Long userId);

    TokensResponseDTO findByToken(String token);

    // 매 분마다 실행되는 스케줄러
    @Scheduled(cron = "* 1 * * * *")
    void checkTokenExpiration();
}
