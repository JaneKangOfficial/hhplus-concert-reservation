package io.hhplus.concert.domain.concerts.business.serviceImpl;

import io.hhplus.concert.common.exception.CustomException;
import io.hhplus.concert.common.status.ReservationsStatus;
import io.hhplus.concert.common.status.SeatsStatus;
import io.hhplus.concert.domain.concerts.business.exception.ConcertsException;
import io.hhplus.concert.domain.concerts.business.exception.DatesException;
import io.hhplus.concert.domain.concerts.business.exception.SeatsException;
import io.hhplus.concert.domain.concerts.business.repository.ConcertsRepository;
import io.hhplus.concert.domain.concerts.business.repository.DatesRepository;
import io.hhplus.concert.domain.concerts.business.repository.SeatsRepository;
import io.hhplus.concert.domain.concerts.business.service.ConcertsService;
import io.hhplus.concert.domain.concerts.infrastructure.entity.ConcertEntity;
import io.hhplus.concert.domain.concerts.infrastructure.entity.DatesEntity;
import io.hhplus.concert.domain.concerts.infrastructure.entity.SeatsEntity;
import io.hhplus.concert.domain.concerts.presentation.dto.response.ConcertsResponseDTO;
import io.hhplus.concert.domain.concerts.presentation.dto.response.DatesResponseDTO;
import io.hhplus.concert.domain.concerts.presentation.dto.response.SeatsResponseDTO;
import io.hhplus.concert.domain.reservations.business.exception.ReservationsException;
import io.hhplus.concert.domain.reservations.business.repository.ReservationsRepository;
import io.hhplus.concert.domain.reservations.infrastructure.entity.ReservationsEntity;
import io.hhplus.concert.domain.reservations.presentation.dto.request.ReservationsRequestDTO;
import io.hhplus.concert.domain.reservations.presentation.dto.response.ReservationsResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.LogLevel;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConcertsServiceImpl implements ConcertsService {

    private static final Logger log = LoggerFactory.getLogger(ConcertsServiceImpl.class);
    private final DatesRepository datesRepository;
    private final SeatsRepository seatsRepository;
    private final ConcertsRepository concertsRepository;
    private final ReservationsRepository reservationsRepository;

    public ConcertsServiceImpl(DatesRepository datesRepository, ConcertsRepository concertsRepository, SeatsRepository seatsRepository, ReservationsRepository reservationsRepository) {
        this.datesRepository = datesRepository;
        this.concertsRepository = concertsRepository;
        this.seatsRepository = seatsRepository;
        this.reservationsRepository = reservationsRepository;
    }

    LocalDateTime currentDateTime = LocalDateTime.now();

    public void checkSeatsExpiration() {

        // 잠금 상태 좌석 조회
        List<SeatsEntity> seatsEntityList = seatsRepository.findAllByStatus(SeatsStatus.UNAVAILABLE);

        // 좌석별로 만료 체크 및 업데이트
        for (SeatsEntity seat : seatsEntityList) {
            // 만료 시간이 현재 시간보다 미래이면 활성 상태로 변경
            if (seat.getLockUntil() != null && seat.getLockUntil().isBefore(currentDateTime)) {
                seat.setStatus(SeatsStatus.AVAILABLE);
                seat.setLockUntil(null);
                seatsRepository.save(seat); // 상태 업데이트
            }
        }
    }

    @Override
    @Cacheable(value = "concertList", key = "#root.methodName")
    public List<ConcertsResponseDTO> getConcerts() {
        Long startTime = System.currentTimeMillis();
        // 콘서트 목록 가져오기
        List<ConcertEntity> concertEntityList = concertsRepository.findAll();

        if (concertEntityList.isEmpty()) {
            throw new CustomException(ConcertsException.EMPTY_CONCERT, LogLevel.INFO, concertEntityList);
        }

        Long endTime = System.currentTimeMillis();
        log.debug("소요 시간 {}", (endTime - startTime) + "ms");

        return concertEntityList.stream()
                .map(ConcertsResponseDTO::convertToDTO)
                .collect(Collectors.toList());
    }


    // 예약 가능 날짜 조회
    @Override
    public List<DatesResponseDTO> getDates(Long concertId) {

        List<DatesEntity> datesEntityList = datesRepository.findByConcertId(concertId);

        if (datesEntityList.isEmpty()) {
            throw new CustomException(DatesException.EMPTY_DATES, LogLevel.INFO, datesEntityList);
        }

        return datesEntityList.stream()
                .map(DatesResponseDTO::convertToDTO)
                .collect(Collectors.toList());
    }

    // 예약 가능 좌석 조회
    @Override
    public List<SeatsResponseDTO> getSeats(Long concertId, Long dateId, Long userId) {

        // 5분 지난 좌석 잠금 해제
        checkSeatsExpiration();

        List<Long> seatNumList = seatsRepository.findSeatNumbersByConcertIdAndDateId(concertId, dateId);

        if (seatNumList.isEmpty()) {
            throw new CustomException(SeatsException.EMPTY_SEATS, LogLevel.INFO, seatNumList);
        }

        return seatNumList.stream()
                .map(SeatsResponseDTO::new)
                .collect(Collectors.toList());

//        List<SeatsEntity> seatsEntityList = concertsRepository.findSeatNumbersByConcertIdAndDateId(concertId, dateId);
//        return seatsEntityList.stream()
//                .map(SeatsResponseDTO::convertToDTO)
//                .collect(Collectors.toList());

    }

    // 예약 요청
    @Override
    @Transactional
    public ReservationsResponseDTO reservations(ReservationsRequestDTO reservationsRequestDTO) {
        Long startTime = System.currentTimeMillis();
        log.debug("{} 유저 획득, reservationsRequestDTO.getUserId(): {}", Thread.currentThread().getName(), reservationsRequestDTO.getUserId());

        ReservationsEntity reservationsEntityRst = new ReservationsEntity();

        try {
            // 선택한 좌석이 예약 가능하면 예약 진행
            Optional<SeatsEntity> seatsEntity = seatsRepository.findById(reservationsRequestDTO.getSeatId());
            log.debug("{} , seatsEntity: {}", Thread.currentThread().getName(), seatsEntity);    // 같은 값 조회

            if (seatsEntity.isPresent() && seatsEntity.get().getStatus() == SeatsStatus.AVAILABLE) {
                seatsEntity.get().setUserId(reservationsRequestDTO.getUserId());

                // 선택한 좌석 잠금
//                seatsRepository.updateStatusAndLockuntil(seatsEntity.get().getId(), SeatsStatus.UNAVAILABLE, currentDateTime.plusMinutes(5));
                seatsRepository.updateStatusAndLockuntilWithLock(seatsEntity.get());
                log.debug("{} update, seatsEntity: {} ", Thread.currentThread().getName(), seatsEntity.get());

                // 예약
                reservationsRequestDTO.setStatus(ReservationsStatus.APPLY);
                reservationsEntityRst = reservationsRepository.save(ReservationsRequestDTO.convertToEntity(reservationsRequestDTO));
                log.debug("{} , reservationsEntity: {}", Thread.currentThread().getName(), reservationsEntityRst);

            } else {
                throw new CustomException(ReservationsException.UNAVAILABLE_RESERVATION, LogLevel.INFO, seatsEntity);
            }
        } catch (Exception e) {
            log.debug("Exception {} because: {}", Thread.currentThread().getName(), e.getMessage());
        }

        Long endTime = System.currentTimeMillis();
        log.debug("{} 유저 종료, 소요 시간 {}", Thread.currentThread().getName(), (endTime - startTime) + "ms");

        return ReservationsResponseDTO.convertToDTO(reservationsEntityRst);
    }


}
