package io.hhplus.concert.domain.tokens.infrastructure.repositoryImpl;

import io.hhplus.concert.common.status.SeatReservationsStatus;
import io.hhplus.concert.domain.tokens.infrastructure.entity.TokensEntity;
import io.hhplus.concert.domain.tokens.presentation.dto.response.TokensResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TokensJpaRepository extends JpaRepository<TokensEntity, Long> {

    Optional<TokensEntity> findByUserId(Long userId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE TOKEN SET TOKEN = :token WHERE USER_ID = :userId", nativeQuery = true)
    void updateSetTokenByUserId(Long userId, String token);

    TokensResponseDTO findByToken(String token);

    List<TokensEntity> findByStatus(SeatReservationsStatus.TokensStatus tokensStatus);
}

