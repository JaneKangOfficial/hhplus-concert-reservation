package io.hhplus.concert.domain.tokens.infrastructure.repositoryImpl;

import io.hhplus.concert.domain.tokens.infrastructure.entity.TokensEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface TokensJpaRepository extends JpaRepository<TokensEntity, Long> {

    Optional<TokensEntity> findByUserId(Long userId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE TOKEN SET TOKEN = :token WHERE USER_ID = :userId", nativeQuery = true)
    void updateSetTokenByUserId(Long userId, String token);
}

