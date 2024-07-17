package io.hhplus.concert.domain.tokens.business.repository;

import io.hhplus.concert.common.status.SeatReservationsStatus;
import io.hhplus.concert.domain.tokens.infrastructure.entity.TokensEntity;
import io.hhplus.concert.domain.tokens.presentation.dto.response.TokensResponseDTO;

import java.util.List;
import java.util.Optional;

public interface TokensRepository {
    Optional<TokensEntity> findByUserId(Long userId);

    List<TokensEntity> findAll();

    void save(TokensEntity token);

    void updateSetTokenByUserId(Long userId, String token);

    TokensResponseDTO findByToken(String token);

    List<TokensEntity> findByStatus(SeatReservationsStatus.TokensStatus tokensStatus);
}
