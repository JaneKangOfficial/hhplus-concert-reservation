package io.hhplus.concert.domain.points.business.serviceImpl;

import io.hhplus.concert.domain.concerts.business.repository.UsersRepository;
import io.hhplus.concert.domain.concerts.infrastructure.entity.UsersEntity;
import io.hhplus.concert.domain.concerts.presentation.dto.response.UsersResponseDTO;
import io.hhplus.concert.common.status.PointsType;
import io.hhplus.concert.domain.points.business.repository.PointsRepository;
import io.hhplus.concert.domain.points.business.service.PointsService;
import io.hhplus.concert.domain.points.presentation.dto.request.PointsRequestDTO;
import io.hhplus.concert.domain.points.presentation.dto.response.PointsResponseDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PointsSerivceImpl implements PointsService {

    private final UsersRepository usersRepository;
    private final PointsRepository pointsRepository;

    public PointsSerivceImpl(UsersRepository usersRepository, PointsRepository pointsRepository) {
        this.usersRepository = usersRepository;
        this.pointsRepository = pointsRepository;
    }

    @Override
    public UsersResponseDTO balances(Long userId) {
        Optional<UsersEntity> userInfo = usersRepository.findByUserId(userId);
        return UsersResponseDTO.convertToDTO(userInfo);
    }

    @Override
    public PointsResponseDTO chargePoint(PointsRequestDTO pointsRequestDTO) {
        UsersResponseDTO userInfo = balances(pointsRequestDTO.getUserId());

        Long point = pointsRequestDTO.getPoint(); // 충전할 포인트
        Long currentTotal = userInfo.getPoint();    // 현재 포인트
        Long newTotal = 0L; // 잔액
        if (pointsRequestDTO.getType() == PointsType.CHARGE) {  // 충전
            newTotal = currentTotal + point;
        } else {    // 차감
            newTotal = currentTotal - point;
        }
        pointsRequestDTO.setTotal(newTotal);

        pointsRepository.save(PointsResponseDTO.convertToEntity(pointsRequestDTO));
        usersRepository.updatePointByUserId(UsersResponseDTO.convertToEntity(pointsRequestDTO));

        return new PointsResponseDTO(newTotal);

    }
}
