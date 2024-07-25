package io.hhplus.concert.domain.points.business.serviceImpl;

import io.hhplus.concert.common.exception.CustomException;
import io.hhplus.concert.common.status.PointsType;
import io.hhplus.concert.domain.concerts.business.repository.UsersRepository;
import io.hhplus.concert.domain.concerts.infrastructure.entity.UsersEntity;
import io.hhplus.concert.domain.concerts.presentation.dto.response.UsersResponseDTO;
import io.hhplus.concert.domain.points.business.exception.PointsException;
import io.hhplus.concert.domain.points.business.repository.PointsRepository;
import io.hhplus.concert.domain.points.business.service.PointsService;
import io.hhplus.concert.domain.points.infrastructure.entity.PointHistoryEntity;
import io.hhplus.concert.domain.points.presentation.dto.request.PointsRequestDTO;
import io.hhplus.concert.domain.points.presentation.dto.response.PointsResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PointsSerivceImpl implements PointsService {

    private static final Logger log = LoggerFactory.getLogger(PointsSerivceImpl.class);
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
    @Transactional
    public PointsResponseDTO chargePoint(PointsRequestDTO pointsRequestDTO) {
        Long startTime = System.currentTimeMillis();

        Long point = pointsRequestDTO.getPoint(); // 충전할 포인트
        if (point <= 0) {
            throw new CustomException(PointsException.LOW_CHARGE, LogLevel.INFO, point);
        }

        log.info("{} 유저 획득, {} point", Thread.currentThread().getName(), point);

        UsersResponseDTO userInfo = balances(pointsRequestDTO.getUserId());
        Long currentTotal = userInfo.getPoint();    // 현재 포인트

        Long newTotal = 0L; // 잔액
        if (pointsRequestDTO.getType() == PointsType.CHARGE) {  // 충전
            newTotal = currentTotal + point;
        } else {    // 차감
            newTotal = currentTotal - point;
        }
        pointsRequestDTO.setPoint(point);
        pointsRequestDTO.setTotal(newTotal);

        try {
            PointHistoryEntity pointHistoryEntity = pointsRepository.save(PointsResponseDTO.convertToEntity(pointsRequestDTO));
//            log.info("{} pointHistoryEntity: {}", Thread.currentThread().getName(), pointHistoryEntity);

            UsersEntity usersEntity = usersRepository.findByUserId(pointsRequestDTO.getUserId()).orElseThrow();
            log.info("{} usersEntity: {}", Thread.currentThread().getName(), usersEntity);  // 같은 값 조회
            usersEntity.setPoint(newTotal);

            usersRepository.updatePointByUserId(usersEntity);
            log.info("{} update, {} currentTotal, {} point, {} 포인트, {} newTotal", Thread.currentThread().getName(), currentTotal, point, pointHistoryEntity.getTotal(), newTotal);

        } catch (Exception e) {
            log.info("Exception {} because: {}", Thread.currentThread().getName(), e.getMessage());
        }

        Long endTime = System.currentTimeMillis();
        log.info("{} 유저 종료, 소요 시간 {}", Thread.currentThread().getName(), (endTime - startTime) + "ms");

        return new PointsResponseDTO(newTotal);

    }
}
