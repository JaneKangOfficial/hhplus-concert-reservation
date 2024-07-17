package io.hhplus.concert.domain.points.presentation.dto.request;

import io.hhplus.concert.common.status.PointsType;

public class PointsRequestDTO {

    private Long userId;

    private Long point;

    private PointsType type;

    private Long total;

    public PointsRequestDTO(Long userId, Long point, PointsType type) {
        this.userId = userId;
        this.point = point;
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPoint() {
        return point;
    }

    public void setPoint(Long point) {
        this.point = point;
    }

    public PointsType getType() {
        return type;
    }

    public void setType(PointsType type) {
        this.type = type;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
