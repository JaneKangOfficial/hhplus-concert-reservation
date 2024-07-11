package io.hhplus.concert.domain.tokens.business.serviceImpl;

import io.hhplus.concert.domain.tokens.presentation.dto.request.TokensRequestDTO;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TokenRequestQueue {
    private static final BlockingQueue<TokensRequestDTO> queue = new LinkedBlockingQueue<>();

    public static void addRequest(TokensRequestDTO request) {
        queue.add(request);
//        return queue.size();
    }

    public static TokensRequestDTO takeRequest() throws InterruptedException {
        return queue.take();
    }
}
