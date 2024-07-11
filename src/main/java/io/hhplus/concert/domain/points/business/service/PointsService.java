package io.hhplus.concert.domain.points.business.service;

import io.hhplus.concert.domain.concerts.presentation.dto.response.UsersResponseDTO;
import io.hhplus.concert.domain.points.presentation.dto.request.PointsRequestDTO;
import io.hhplus.concert.domain.points.presentation.dto.response.PointsResponseDTO;

public interface PointsService {
    UsersResponseDTO balances(Long userId);

    PointsResponseDTO chargePoint(PointsRequestDTO pointsRequestDTO);
}
