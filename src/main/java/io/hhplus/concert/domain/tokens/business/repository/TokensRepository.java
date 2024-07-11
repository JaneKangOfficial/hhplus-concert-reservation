package io.hhplus.concert.domain.tokens.business.repository;

import io.hhplus.concert.domain.tokens.infrastructure.entity.TokensEntity;

import java.util.List;
import java.util.Optional;

public interface TokensRepository {
    Optional<TokensEntity> findByUserId(Long userId);

    List<TokensEntity> findAll();

    void save(TokensEntity token);

    void updateSetTokenByUserId(Long userId, String token);
}
