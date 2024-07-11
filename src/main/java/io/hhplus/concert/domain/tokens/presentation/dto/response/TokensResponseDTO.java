package io.hhplus.concert.domain.tokens.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.hhplus.concert.domain.tokens.infrastructure.entity.TokensEntity;

import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokensResponseDTO {

    private Long userId;

    private String token;

    public TokensResponseDTO(Long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public TokensResponseDTO(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public static TokensResponseDTO converter(Optional<TokensEntity> tokensEntityOptional) {
        TokensEntity tokensEntity = tokensEntityOptional.get();
        return new TokensResponseDTO(tokensEntity.getUserId(), tokensEntity.getToken());
    }


}
