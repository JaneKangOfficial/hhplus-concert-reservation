package io.hhplus.concert.domain.points.business.event;

import io.hhplus.concert.common.status.PointsType;
import io.hhplus.concert.domain.points.infrastructure.entity.PointHistoryEntity;
import lombok.Data;

@Data
public class PointEvent {
    private final Long userId;

    private final PointsType type;

    private final Long point;

    private final Long total;

    public PointEvent(Long userId, PointsType type, Long point, Long total) {
        this.userId = userId;
        this.type = type;
        this.point = point;
        this.total = total;
    }

    public static PointHistoryEntity convertToEntity(PointEvent pointEvent) {
        return new PointHistoryEntity(pointEvent.getUserId(), pointEvent.getType(), pointEvent.getPoint(), pointEvent.getTotal());
    }

}
