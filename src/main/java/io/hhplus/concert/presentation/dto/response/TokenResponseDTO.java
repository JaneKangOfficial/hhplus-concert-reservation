package io.hhplus.concert.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenResponseDTO {

    private Long userId;

    private String token;

    public TokenResponseDTO(Long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public TokenResponseDTO(String token) {
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
}
