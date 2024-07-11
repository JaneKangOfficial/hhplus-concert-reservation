package io.hhplus.concert.domain.tokens.infrastructure.entity;

import io.hhplus.concert.domain.tokens.business.entity.TokensStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Table(name = "token")
@NoArgsConstructor
@Data
public class TokensEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("토큰 PK")
    private Long id;

    @Comment("사용자 FK")
    private Long userId;

    @Comment("토큰")
    private String token;

    @Comment("토큰 상태")
    @Enumerated(EnumType.STRING)
    private TokensStatus status;

    @Comment("생성일")
    private LocalDateTime createdAt;

    @Comment("만료일")
    private LocalDateTime expirationAt;

    public TokensEntity(Long userId, String token) {
        this.userId = userId;
        this.token = token;
        this.status = TokensStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
        this.expirationAt = LocalDateTime.now().plusMinutes(5);
    }

}
