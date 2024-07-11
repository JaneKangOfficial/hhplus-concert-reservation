package io.hhplus.concert.domain.tokens.business.service;

import io.hhplus.concert.domain.tokens.presentation.dto.response.TokensResponseDTO;

public interface TokensService {

    void requestTokenIssuance(Long userId);
    
    TokensResponseDTO issueToken(Long userId);
}
