package io.hhplus.concert.presentation.points.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.hhplus.concert.infrastructure.entity.PointsStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PointsResponseDTO {

    private Long pointId;

    private Long userId;

    private Integer point;

    private Integer total;

    private PointsStatus status;

    public PointsResponseDTO(Integer total) {
        this.total = total;
    }

    public Long getPointId() {
        return pointId;
    }

    public void setPointId(Long pointId) {
        this.pointId = pointId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public PointsStatus getStatus() {
        return status;
    }

    public void setStatus(PointsStatus status) {
        this.status = status;
    }
}
