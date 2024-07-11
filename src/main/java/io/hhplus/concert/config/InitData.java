package io.hhplus.concert.config;

import io.hhplus.concert.domain.tokens.business.entity.TokensStatus;
import io.hhplus.concert.domain.tokens.infrastructure.entity.TokensEntity;
import io.hhplus.concert.domain.tokens.infrastructure.repositoryImpl.TokensJpaRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class InitData implements ApplicationRunner {

    private final TokensJpaRepository tokensJpaRepository;

    public InitData(TokensJpaRepository tokensJpaRepository) {
        this.tokensJpaRepository = tokensJpaRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 초기 데이터 생성 - 토큰 테이블
        TokensEntity tokensEntity1 = new TokensEntity();
        tokensEntity1.setUserId(1L);
        tokensEntity1.setToken("2a9ec704-75b9-4993-aaf5-5a80cfc3de04");
        tokensEntity1.setStatus(TokensStatus.ACTIVE);
        tokensEntity1.setCreatedAt(LocalDateTime.now());
        tokensEntity1.setExpirationAt(LocalDateTime.now().plusMinutes(5));
        tokensJpaRepository.save(tokensEntity1);

        TokensEntity tokensEntity2 = new TokensEntity();
        tokensEntity2.setUserId(2L);
        tokensEntity2.setToken("de8482a6-82fd-484f-8010-566e7a27e74d");
        tokensEntity2.setStatus(TokensStatus.EXPIRATION);
        tokensEntity2.setCreatedAt(LocalDateTime.now());
        tokensEntity2.setExpirationAt(LocalDateTime.now().minusMinutes(5)); // 테스트를 위한 마이너스 만료 시간으로 설정
        tokensJpaRepository.save(tokensEntity2);

    }


}