package io.hhplus.concert.domain.payments.business.serviceImpl;

import io.hhplus.concert.domain.concerts.business.repository.ConcertsRepository;
import io.hhplus.concert.domain.concerts.business.repository.DatesRepository;
import io.hhplus.concert.domain.concerts.business.repository.SeatsRepository;
import io.hhplus.concert.domain.concerts.business.repository.UsersRepository;
import io.hhplus.concert.domain.concerts.infrastructure.entity.ConcertEntity;
import io.hhplus.concert.domain.concerts.infrastructure.entity.DatesEntity;
import io.hhplus.concert.domain.concerts.infrastructure.entity.SeatsEntity;
import io.hhplus.concert.domain.concerts.infrastructure.entity.UsersEntity;
import io.hhplus.concert.common.status.PaymentsStatus;
import io.hhplus.concert.domain.payments.business.repository.PaymentsHistoryRepository;
import io.hhplus.concert.domain.payments.business.repository.PaymentsRepository;
import io.hhplus.concert.domain.payments.business.service.PaymentsService;
import io.hhplus.concert.domain.payments.infrastructure.entity.PaymentsEntity;
import io.hhplus.concert.domain.payments.infrastructure.entity.PaymentsHistoryEntity;
import io.hhplus.concert.domain.payments.presentation.dto.request.PaymentsRequestDTO;
import io.hhplus.concert.domain.payments.presentation.dto.response.PaymentsResponseDTO;
import io.hhplus.concert.common.status.PointsType;
import io.hhplus.concert.domain.points.business.repository.PointsRepository;
import io.hhplus.concert.domain.points.infrastructure.entity.PointHistoryEntity;
import io.hhplus.concert.domain.reservations.business.repository.ReservationsRepository;
import io.hhplus.concert.domain.tokens.business.repository.TokensRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentsServiceImpl implements PaymentsService {

    private final PaymentsRepository paymentsRepository;
    private final ConcertsRepository concertsRepository;
    private final UsersRepository usersRepository;
    private final PaymentsHistoryRepository paymentsHistoryRepository;
    private final ReservationsRepository reservationsRepository;
    private final DatesRepository datesRepository;
    private final SeatsRepository seatsRepository;
    private final PointsRepository pointsRepository;
    private final TokensRepository tokensRepository;

    public PaymentsServiceImpl(PaymentsRepository paymentsRepository, ConcertsRepository concertsRepository, UsersRepository usersRepository, PaymentsHistoryRepository paymentsHistoryRepository, ReservationsRepository reservationsRepository, DatesRepository datesRepository, SeatsRepository seatsRepository, PointsRepository pointsRepository, TokensRepository tokensRepository) {
        this.paymentsRepository = paymentsRepository;
        this.concertsRepository = concertsRepository;
        this.usersRepository = usersRepository;
        this.paymentsHistoryRepository = paymentsHistoryRepository;
        this.reservationsRepository = reservationsRepository;
        this.datesRepository = datesRepository;
        this.seatsRepository = seatsRepository;
        this.pointsRepository = pointsRepository;
        this.tokensRepository = tokensRepository;
    }

    @Override
    public PaymentsResponseDTO payments(String token, PaymentsRequestDTO paymentsRequestDTO) {

        // 콘서트 정보 가져오기
        Optional<ConcertEntity> concertEntity = concertsRepository.findById(paymentsRequestDTO.getConcertId());

        // 포인트 확인
        Optional<UsersEntity> userInfo = usersRepository.findByUserId(paymentsRequestDTO.getUserId());
        if (concertEntity.get().getPrice() > userInfo.get().getPoint()) {
            throw new IllegalArgumentException("포인트가 부족합니다.");
        }

        // 포인트 차감
        Long total = userInfo.get().getPoint() - concertEntity.get().getPrice();

        // 포인트 저장
        usersRepository.updatePointByUserId(new UsersEntity(paymentsRequestDTO.getUserId(), total));

        // 포인트 히스토리 저장
        PointHistoryEntity pointHistoryEntity = PointHistoryEntity.builder()
                .userId(paymentsRequestDTO.getUserId())
                .point(concertEntity.get().getPrice())
                .type(PointsType.USE)
                .total(total)
                .build();
        pointsRepository.save(pointHistoryEntity);

        // 날짜 정보 가져오기
        Optional<DatesEntity> datesEntity = datesRepository.findById(paymentsRequestDTO.getDateId());

        // 좌석 정보 가져오기
        Optional<SeatsEntity> seatsEntity = seatsRepository.findById(paymentsRequestDTO.getSeatId());

        // 결제
        PaymentsEntity paymentsEntity = paymentsRepository.save(paymentsRequestDTO.convertToEntity(paymentsRequestDTO, concertEntity.get().getPrice(), PaymentsStatus.COMPLETE));

        // 결제 히스토리
        PaymentsHistoryEntity paymentsHistoryEntity = PaymentsHistoryEntity.builder()
                .paymentId(paymentsEntity.getId())
                .userId(paymentsRequestDTO.getUserId())
                .concertTitle(concertEntity.get().getTitle())
                .concertPrice(concertEntity.get().getPrice())
                .concertDate(datesEntity.get().getConcertDate())
                .concertSeatNum(seatsEntity.get().getNum())
                .build();

        paymentsHistoryRepository.save(paymentsHistoryEntity);

        return PaymentsResponseDTO.convertToDTO(paymentsEntity);
    }
}
