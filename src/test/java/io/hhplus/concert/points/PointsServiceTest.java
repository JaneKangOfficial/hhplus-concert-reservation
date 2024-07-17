package io.hhplus.concert.points;

import io.hhplus.concert.domain.concerts.presentation.dto.response.UsersResponseDTO;
import io.hhplus.concert.common.status.PointsType;
import io.hhplus.concert.domain.points.business.repository.PointsRepository;
import io.hhplus.concert.domain.points.business.serviceImpl.PointsSerivceImpl;
import io.hhplus.concert.domain.points.infrastructure.entity.PointHistoryEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PointsServiceTest {

    @InjectMocks
    private PointsSerivceImpl pointsSerivceImpl;

    @Mock
    private PointsRepository pointsRepository;

    @Test
    @DisplayName("포인트 조회 테스트")
    void getPointTest() {
        // given
        Long userId = 1L;
        Integer point = 10000;

        // when
        UsersResponseDTO usersResponseDTO = pointsSerivceImpl.balances(userId);

        // then
        assertEquals(userId, usersResponseDTO.getUserId());
        assertEquals(point, usersResponseDTO.getPoint());

    }

    @Test
    @DisplayName("포인트 충전 테스트")
    public void chargePointTest() {
        // given
        PointHistoryEntity pointHistoryEntity = new PointHistoryEntity();
        pointHistoryEntity.setUserId(1L);
        pointHistoryEntity.setPoint(100L);
        pointHistoryEntity.setType(PointsType.CHARGE);

        // when
        pointsRepository.save(pointHistoryEntity);

        // then
        verify(pointsRepository).save(any(PointHistoryEntity.class));
    }

}
