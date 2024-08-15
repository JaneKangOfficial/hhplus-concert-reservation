package io.hhplus.concert.domain.payments.business.serviceImpl;

import io.hhplus.concert.common.exception.CustomException;
import io.hhplus.concert.common.status.PaymentsStatus;
import io.hhplus.concert.common.status.PointsType;
import io.hhplus.concert.domain.concerts.business.repository.ConcertsRepository;
import io.hhplus.concert.domain.concerts.business.repository.DatesRepository;
import io.hhplus.concert.domain.concerts.business.repository.SeatsRepository;
import io.hhplus.concert.domain.concerts.business.repository.UsersRepository;
import io.hhplus.concert.domain.concerts.infrastructure.entity.ConcertEntity;
import io.hhplus.concert.domain.concerts.infrastructure.entity.DatesEntity;
import io.hhplus.concert.domain.concerts.infrastructure.entity.SeatsEntity;
import io.hhplus.concert.domain.concerts.infrastructure.entity.UsersEntity;
import io.hhplus.concert.domain.payments.business.event.PaymentEvent;
import io.hhplus.concert.domain.payments.business.event.PaymentEventPublisher;
import io.hhplus.concert.domain.payments.business.exception.PaymentsException;
import io.hhplus.concert.domain.payments.business.message.PaymentMessageOutboxWriter;
import io.hhplus.concert.domain.payments.business.message.PaymentMessageSender;
import io.hhplus.concert.domain.payments.business.repository.PaymentsHistoryRepository;
import io.hhplus.concert.domain.payments.business.repository.PaymentsRepository;
import io.hhplus.concert.domain.payments.business.service.PaymentsService;
import io.hhplus.concert.domain.payments.infrastructure.entity.PaymentsEntity;
import io.hhplus.concert.domain.payments.presentation.dto.request.PaymentsRequestDTO;
import io.hhplus.concert.domain.payments.presentation.dto.response.PaymentsResponseDTO;
import io.hhplus.concert.domain.points.business.event.PointEvent;
import io.hhplus.concert.domain.points.business.event.PointEventPublisher;
import io.hhplus.concert.domain.points.business.repository.PointsRepository;
import io.hhplus.concert.domain.queues.business.repository.QueueRepository;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PaymentsServiceImpl implements PaymentsService {

    private static final String ACTIVE_QUEUES = "activeQueues";

    private final PaymentsRepository paymentsRepository;
    private final ConcertsRepository concertsRepository;
    private final UsersRepository usersRepository;
    private final PaymentsHistoryRepository paymentsHistoryRepository;
    private final DatesRepository datesRepository;
    private final SeatsRepository seatsRepository;
    private final PointsRepository pointsRepository;
    private final QueueRepository queueRepository;
    private final PaymentEventPublisher paymentEventPublisher;
    private final PointEventPublisher pointEventPublisher;
    private final PaymentMessageOutboxWriter paymentMessageOutboxWriter;
    private final PaymentMessageSender paymentMessageSender;

    public PaymentsServiceImpl(PaymentsRepository paymentsRepository, ConcertsRepository concertsRepository, UsersRepository usersRepository
            , PaymentsHistoryRepository paymentsHistoryRepository, DatesRepository datesRepository, SeatsRepository seatsRepository
            , PointsRepository pointsRepository, QueueRepository queueRepository, PaymentEventPublisher paymentEventPublisher, PointEventPublisher pointEventPublisher, PaymentMessageOutboxWriter paymentMessageOutboxWriter, PaymentMessageSender paymentMessageSender) {
        this.paymentsRepository = paymentsRepository;
        this.concertsRepository = concertsRepository;
        this.usersRepository = usersRepository;
        this.paymentsHistoryRepository = paymentsHistoryRepository;
        this.datesRepository = datesRepository;
        this.seatsRepository = seatsRepository;
        this.pointsRepository = pointsRepository;
        this.queueRepository = queueRepository;
        this.paymentEventPublisher = paymentEventPublisher;
        this.pointEventPublisher = pointEventPublisher;
        this.paymentMessageOutboxWriter = paymentMessageOutboxWriter;
        this.paymentMessageSender = paymentMessageSender;
    }

    @Override
    @Transactional
    public PaymentsResponseDTO payments(String token, PaymentsRequestDTO paymentsRequestDTO) {

        // 콘서트 정보 가져오기
        Optional<ConcertEntity> concertEntity = concertsRepository.findById(paymentsRequestDTO.getConcertId());

        // 포인트 확인
        Optional<UsersEntity> userInfo = usersRepository.findByUserId(paymentsRequestDTO.getUserId());
        if (concertEntity.get().getPrice() > userInfo.get().getPoint()) {
            throw new CustomException(PaymentsException.LACK_OF_POINT, LogLevel.INFO, userInfo.get().getPoint());
        }

        // 포인트 차감
        Long total = userInfo.get().getPoint() - concertEntity.get().getPrice();

        // 포인트 저장
        usersRepository.updatePointByUserId(new UsersEntity(paymentsRequestDTO.getUserId(), total));

        // 날짜 정보 가져오기
        Optional<DatesEntity> datesEntity = datesRepository.findById(paymentsRequestDTO.getDateId());

        // 좌석 정보 가져오기
        Optional<SeatsEntity> seatsEntity = seatsRepository.findById(paymentsRequestDTO.getSeatId());

        // 결제
        PaymentsEntity paymentsEntity = paymentsRepository.save(paymentsRequestDTO.convertToEntity(paymentsRequestDTO, concertEntity.get().getPrice(), PaymentsStatus.COMPLETE));

        // 활성 토큰 만료
        queueRepository.expireQueue(ACTIVE_QUEUES + ":" + paymentsRequestDTO.getUserId(), 1);  // TTL 설정 1초 후 만료되도록

        // 부가로직인 포인트 히스토리 저장 -> 이벤트
        pointEventPublisher.savePointHistory(new PointEvent(paymentsRequestDTO.getUserId(), PointsType.USE, concertEntity.get().getPrice(), total));

        // 부가로직인 결제 히스토리 저장 -> 이벤트
        paymentEventPublisher.savePaymentHistory(new PaymentEvent(paymentsEntity.getId(), paymentsRequestDTO.getUserId(), concertEntity.get().getTitle(), concertEntity.get().getPrice(), datesEntity.get().getConcertDate(), seatsEntity.get().getNum()));

        return PaymentsResponseDTO.convertToDTO(paymentsEntity);
    }

    // payments Transaction이 commit된 후에 실행되는 event
    @Override
    public void calculatePoint(PointEvent pointEvent) {
        // 포인트 히스토리 저장
        pointsRepository.save(PointEvent.convertToEntity(pointEvent));
    }

    // payments Transaction이 commit된 후에 실행되는 event
    @Override
    public void savePaymentHistory(PaymentEvent paymentEvent) {
        // 결제 히스토리 저장
        paymentsHistoryRepository.save(PaymentEvent.convertToEntity(paymentEvent));
    }
}
