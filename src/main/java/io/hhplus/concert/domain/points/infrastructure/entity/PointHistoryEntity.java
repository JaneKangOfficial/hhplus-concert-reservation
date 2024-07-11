package io.hhplus.concert.domain.points.infrastructure.entity;

import io.hhplus.concert.domain.points.business.entity.PointsType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Table(name = "point_history")
@NoArgsConstructor
@Data
public class PointHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("포인트 히스토리 PK")
    private Long id;

    @Comment("사용자 FK")
    private Long userId;

    @Comment("사용 타입")
    private PointsType type;

    @Comment("포인트")
    private Long point;

    @Comment("총 포인트")
    private Long total;

    @Comment("생성일")
    private LocalDateTime createdAt;

    public PointHistoryEntity(Long userId, PointsType type, Long point, Long total) {
        this.userId = userId;
        this.type = type;
        this.point = point;
        this.total = total;
        this.createdAt = LocalDateTime.now();
    }

}
