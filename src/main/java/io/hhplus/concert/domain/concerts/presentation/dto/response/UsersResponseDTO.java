package io.hhplus.concert.domain.concerts.presentation.dto.response;

import io.hhplus.concert.domain.concerts.infrastructure.entity.UsersEntity;
import io.hhplus.concert.domain.points.presentation.dto.request.PointsRequestDTO;

import java.util.Optional;

public class UsersResponseDTO {

    private Long userId;

    private Long point;

    public UsersResponseDTO(Long userId, Long point) {
        this.userId = userId;
        this.point = point;
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

    public static UsersResponseDTO convertToDTO(Optional<UsersEntity> usersEntityOptional) {
        UsersEntity usersEntity = usersEntityOptional.get();
        return new UsersResponseDTO(usersEntity.getUserId(), usersEntity.getPoint());
    }

    public static UsersEntity convertToEntity(PointsRequestDTO pointsRequestDTO) {
        return new UsersEntity(pointsRequestDTO.getUserId(), pointsRequestDTO.getTotal());
    }
}
