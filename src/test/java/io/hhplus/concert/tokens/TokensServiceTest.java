package io.hhplus.concert.tokens;

import io.hhplus.concert.domain.tokens.business.repository.TokensRepository;
import io.hhplus.concert.domain.tokens.business.serviceImpl.TokensServiceImpl;
import io.hhplus.concert.domain.tokens.infrastructure.entity.TokensEntity;
import io.hhplus.concert.domain.tokens.presentation.dto.response.TokensResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TokensServiceTest {

    @InjectMocks
    private TokensServiceImpl tokensServiceImpl;

    @Mock
    private TokensRepository tokensRepository;

    @Test
    void issueTokenTest() {
        // given
        Long userId = 1L;
        Optional<TokensEntity> tokensEntity = Optional.of(new TokensEntity(userId, "TOKEN"));
        when(tokensRepository.findByUserId(userId)).thenReturn(tokensEntity);

        // when
        TokensResponseDTO responseDTO = tokensServiceImpl.issueToken(userId);

        //then
        assertEquals(userId, responseDTO.getUserId());
        assertEquals("TOKEN", tokensEntity.get().getToken());

    }
}
