package io.hhplus.concert.domain.points.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.hhplus.concert.common.status.PointsType;
import io.hhplus.concert.domain.points.infrastructure.entity.PointHistoryEntity;
import io.hhplus.concert.domain.points.presentation.dto.request.PointsRequestDTO;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PointsResponseDTO {

    private Long pointId;

    private Long userId;

    private Long point;

    private Long total;

    private PointsType status;

    public PointsResponseDTO(Long total) {
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

    public Long getPoint() {
        return point;
    }

    public void setPoint(Long point) {
        this.point = point;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public PointsType getStatus() {
        return status;
    }

    public void setStatus(PointsType status) {
        this.status = status;
    }

    public static PointHistoryEntity convertToEntity(PointsRequestDTO pointsRequestDTO) {
        return new PointHistoryEntity(pointsRequestDTO.getUserId(), pointsRequestDTO.getType(), pointsRequestDTO.getPoint(), pointsRequestDTO.getTotal());
    }
}
