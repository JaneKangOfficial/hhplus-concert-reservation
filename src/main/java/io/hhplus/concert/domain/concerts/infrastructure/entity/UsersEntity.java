package io.hhplus.concert.domain.concerts.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "user_info")
@NoArgsConstructor
@Data
public class UsersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("사용자 PK")
    @Column(name = "ID")
    private Long id;

    @Comment("사용자 아이디")
    @Column(name = "USER_ID")
    private Long userId;

    @Comment("포인트")
    @Column(name = "POINT")
    private Long point;

    public UsersEntity(Long userId, Long point) {
        this.userId = userId;
        this.point = point;
    }

}
