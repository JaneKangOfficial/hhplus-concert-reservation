package io.hhplus.concert.domain.tokens.infrastructure.repositoryImpl;

import io.hhplus.concert.common.status.SeatReservationsStatus;
import io.hhplus.concert.domain.tokens.business.repository.TokensRepository;
import io.hhplus.concert.domain.tokens.infrastructure.entity.TokensEntity;
import io.hhplus.concert.domain.tokens.presentation.dto.response.TokensResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TokensRepositoryImpl implements TokensRepository {

    private final TokensJpaRepository tokensJpaRepository;

    @Override
    public Optional<TokensEntity> findByUserId(Long userId) {
        return tokensJpaRepository.findByUserId(userId);
    }

    @Override
    public List<TokensEntity> findAll() {
        return tokensJpaRepository.findAll();
    }

    @Override
    public void save(TokensEntity token) {
        tokensJpaRepository.save(token);
    }

    @Override
    public void updateSetTokenByUserId(Long userId, String token) {
        tokensJpaRepository.updateSetTokenByUserId(userId, token);
    }

    @Override
    public TokensResponseDTO findByToken(String token) {
        return tokensJpaRepository.findByToken(token);
    }

    @Override
    public List<TokensEntity> findByStatus(SeatReservationsStatus.TokensStatus tokensStatus) {
        return tokensJpaRepository.findByStatus(tokensStatus);
    }

}
