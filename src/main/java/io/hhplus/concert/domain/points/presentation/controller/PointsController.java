package io.hhplus.concert.domain.points.presentation.controller;

import io.hhplus.concert.domain.concerts.presentation.dto.response.UsersResponseDTO;
import io.hhplus.concert.domain.points.business.service.PointsService;
import io.hhplus.concert.domain.points.presentation.dto.request.PointsRequestDTO;
import io.hhplus.concert.domain.points.presentation.dto.response.PointsResponseDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/points")
public class PointsController {

    private final PointsService pointsService;

    public PointsController(PointsService pointsService) {
        this.pointsService = pointsService;
    }

    /**
     * 잔액 조회 API
     */
    @GetMapping("/balances/{userId}")
    public UsersResponseDTO balances(@PathVariable Long userId) {
        return pointsService.balances(userId);
    }

    /**
     * 잔액 충전 API
     */
    @PostMapping("/charge")
    public PointsResponseDTO chargePoint(@RequestBody PointsRequestDTO pointsRequestDTO) {
        return pointsService.chargePoint(pointsRequestDTO);
    }


}
