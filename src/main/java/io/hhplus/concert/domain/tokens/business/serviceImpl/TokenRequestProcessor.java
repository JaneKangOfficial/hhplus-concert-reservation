package io.hhplus.concert.domain.tokens.business.serviceImpl;

import io.hhplus.concert.domain.tokens.presentation.dto.request.TokensRequestDTO;

public class TokenRequestProcessor implements Runnable {
    private final TokensServiceImpl tokensService;

    public TokenRequestProcessor(TokensServiceImpl tokensService) {
        this.tokensService = tokensService;
    }

    @Override
    public void run() {
        try {
            while (true) {
                TokensRequestDTO request = TokenRequestQueue.takeRequest();
                tokensService.issueToken(request.getUserId());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
