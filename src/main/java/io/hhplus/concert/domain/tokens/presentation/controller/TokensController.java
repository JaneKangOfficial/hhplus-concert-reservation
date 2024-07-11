package io.hhplus.concert.domain.tokens.presentation.controller;

import io.hhplus.concert.domain.tokens.business.service.TokensService;
import io.hhplus.concert.domain.tokens.presentation.dto.response.TokensResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tokens")
public class TokensController {

    private final TokensService tokensService;

    public TokensController(TokensService tokensService) {
        this.tokensService = tokensService;
    }

    /**
     * 유저 토큰 발급 API
     */
    @GetMapping("/issue/{userId}")
    public TokensResponseDTO issueToken(@PathVariable Long userId) {
        tokensService.requestTokenIssuance(userId);
        return tokensService.issueToken(userId);
    }


}
