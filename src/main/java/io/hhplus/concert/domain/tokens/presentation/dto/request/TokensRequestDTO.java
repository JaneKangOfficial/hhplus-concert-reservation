package io.hhplus.concert.domain.tokens.presentation.dto.request;

public class TokensRequestDTO {

    private Long userId;

    public TokensRequestDTO(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
