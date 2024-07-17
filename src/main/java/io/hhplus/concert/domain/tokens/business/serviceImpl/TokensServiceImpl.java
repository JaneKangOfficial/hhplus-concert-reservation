package io.hhplus.concert.domain.tokens.business.serviceImpl;

import io.hhplus.concert.common.status.SeatReservationsStatus;
import io.hhplus.concert.domain.tokens.business.repository.TokensRepository;
import io.hhplus.concert.domain.tokens.business.service.TokensService;
import io.hhplus.concert.domain.tokens.infrastructure.entity.TokensEntity;
import io.hhplus.concert.domain.tokens.presentation.dto.request.TokensRequestDTO;
import io.hhplus.concert.domain.tokens.presentation.dto.response.TokensResponseDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TokensServiceImpl implements TokensService {

    private final TokensRepository tokensRepository;

    public TokensServiceImpl(TokensRepository tokensRepository) {
        this.tokensRepository = tokensRepository;

        // 대기열 처리 스레드 시작
        Thread processorThread = new Thread(new TokenRequestProcessor(this));
        processorThread.start();
    }

    @Override
    public void requestTokenIssuance(Long userId) {
        TokenRequestQueue.addRequest(new TokensRequestDTO(userId));
    }

    public void checkTokenExpiration() {
        LocalDateTime currentDateTime = LocalDateTime.now();

        // 활성상태인 토큰 조회
//        List<TokensEntity> tokensList = tokensRepository.findAll();
        List<TokensEntity> tokensList = tokensRepository.findByStatus(SeatReservationsStatus.TokensStatus.ACTIVE);

        // 토큰별로 만료 체크 및 업데이트
        for (TokensEntity token : tokensList) {
            // 만료 시간이 현재 시간보다 미래이면 만료 상태로 변경
            if (token.getExpirationAt() != null && token.getExpirationAt().isBefore(currentDateTime)) {
                token.setStatus(SeatReservationsStatus.TokensStatus.EXPIRATION);
                tokensRepository.save(token); // 상태 업데이트
            }
        }
    }

    @Override
    public TokensResponseDTO issueToken(Long userId) {

        // 사용자 토큰 가져오기
        Optional<TokensEntity> userTokenInfo = tokensRepository.findByUserId(userId);

        // UUID를 사용하여 토큰 생성
        String newToken = generateToken(userId);

        // 토큰 여부 확인해서 발급
        return confirmToken(userId, userTokenInfo, newToken);
    }

    @Override
    public TokensResponseDTO findByToken(String token) {
        return tokensRepository.findByToken(token);
    }

    private String generateToken(Long userId) {
        return UUID.randomUUID().toString();
    }

    private TokensResponseDTO confirmToken(Long userId, Optional<TokensEntity> userTokenInfo, String newToken) {
        if (!userTokenInfo.isPresent()) {
            // 토큰이 없는 경우 토큰 발행
            TokensEntity newEntity = new TokensEntity(userId, newToken);
            tokensRepository.save(newEntity);

            // DTO로 변환해서 반환
            return new TokensResponseDTO(userId, newToken);
        } else if (userTokenInfo.isPresent() && (userTokenInfo.get().getToken() == null || userTokenInfo.get().getStatus() == SeatReservationsStatus.TokensStatus.EXPIRATION)) {
            // 토큰이 만료된 경우 토큰 발행
            TokensEntity tokensEntity = new TokensEntity(userId, newToken);
            tokensRepository.updateSetTokenByUserId(userId, newToken);

            // DTO로 변환해서 반환
            return TokensResponseDTO.convert(Optional.of(tokensEntity));
        } else {
            // DTO로 변환해서 반환
            return TokensResponseDTO.convert(userTokenInfo);
        }
    }

}
