package io.hhplus.concert.domain.concerts.business.serviceImpl;

import io.hhplus.concert.domain.concerts.business.entity.SeatsStatus;
import io.hhplus.concert.domain.concerts.business.repository.ConcertsRepository;
import io.hhplus.concert.domain.concerts.business.service.ConcertsService;
import io.hhplus.concert.domain.concerts.infrastructure.entity.DatesEntity;
import io.hhplus.concert.domain.concerts.infrastructure.entity.SeatsEntity;
import io.hhplus.concert.domain.concerts.infrastructure.repositoryImpl.SeatsJpaRepository;
import io.hhplus.concert.domain.concerts.presentation.dto.response.DatesResponseDTO;
import io.hhplus.concert.domain.concerts.presentation.dto.response.SeatsResponseDTO;
import io.hhplus.concert.domain.reservations.business.entity.ReservationsStatus;
import io.hhplus.concert.domain.reservations.infrastructure.entity.ReservationsEntity;
import io.hhplus.concert.domain.reservations.infrastructure.respositoryImpl.ReservationsJpaRepository;
import io.hhplus.concert.domain.reservations.presentation.dto.request.ReservationsRequestDTO;
import io.hhplus.concert.domain.reservations.presentation.dto.response.ReservationsResponseDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConcertsServiceImpl implements ConcertsService {

    private final SeatsJpaRepository seatsJpaRepository;
    private final ConcertsRepository concertsRepository;
    private final ReservationsJpaRepository reservationsJpaRepository;

    public ConcertsServiceImpl(ConcertsRepository concertsRepository, SeatsJpaRepository seatsJpaRepository, ReservationsJpaRepository reservationsJpaRepository) {
        this.concertsRepository = concertsRepository;
        this.seatsJpaRepository = seatsJpaRepository;
        this.reservationsJpaRepository = reservationsJpaRepository;
    }

    // 매 분마다 실행되는 스케줄러
//    @Scheduled(cron = "* 1 * * * *")
    public void checkSeatsExpiration() {
        LocalDateTime currentDateTime = LocalDateTime.now();

        // 모든 좌석 조회
        List<SeatsEntity> seatsEntityList = seatsJpaRepository.findAll();

        // 좌석별로 만료 체크 및 업데이트
        for (SeatsEntity seat : seatsEntityList) {
            // 만료 시간이 현재 시간보다 미래이면 만료 상태로 변경
            if (seat.getLockUntil() != null && seat.getLockUntil().isBefore(currentDateTime)) {
                seat.setStatus(SeatsStatus.AVAILABLE);
                seat.setLockUntil(null);
                seatsJpaRepository.save(seat); // 상태 업데이트
            }
        }
    }

    // 예약 가능 날짜 조회
    @Override
    public List<DatesResponseDTO> getDates(Long concertId) {

        List<DatesEntity> datesEntityList = concertsRepository.findByConcertId(concertId);

        return datesEntityList.stream()
                .map(DatesResponseDTO::convertToDTO)
                .collect(Collectors.toList());
    }

    // 예약 가능 좌석 조회
    @Override
    public List<SeatsResponseDTO> getSeats(Long concertId, Long dateId) {

        List<Long> seatNumList = concertsRepository.findSeatNumbersByConcertIdAndDateId(concertId, dateId);

        return seatNumList.stream()
                .map(SeatsResponseDTO::new)
                .collect(Collectors.toList());

//        List<SeatsEntity> seatsEntityList = concertsRepository.findSeatNumbersByConcertIdAndDateId(concertId, dateId);
//        return seatsEntityList.stream()
//                .map(SeatsResponseDTO::convertToDTO)
//                .collect(Collectors.toList());

    }

    // TODO :: 좌석 클릭시 lock_until 현재 시간 + 5분으로 수정, status는 UNAVAILABLE로 수정하여 좌석 잠금

    // 예약 요청
    @Override
    public ReservationsResponseDTO reservations(ReservationsRequestDTO reservationsRequestDTO) {
        Optional<ReservationsEntity> reservationsEntity = Optional.empty();

        // 선택한 좌석이 예약 가능하면 예약 진행
        Optional<SeatsEntity> seatsEntity = seatsJpaRepository.findById(reservationsRequestDTO.getSeatId());

        if (seatsEntity.isPresent() && seatsEntity.get().getStatus() == SeatsStatus.AVAILABLE) {
            reservationsRequestDTO.setStatus(ReservationsStatus.APPLY);
            reservationsJpaRepository.save(ReservationsRequestDTO.convertToEntity(reservationsRequestDTO));

            Long userId = reservationsRequestDTO.getUserId();
            Long concertId = reservationsRequestDTO.getConcertId();
            Long dateId = reservationsRequestDTO.getDateId();
            Long seatId = reservationsRequestDTO.getSeatId();
            reservationsEntity = reservationsJpaRepository.findByUserIdAndConcertIdAndDateIdAndSeatId(userId, concertId, dateId, seatId);
        } else {
            throw new NullPointerException();
        }
        return ReservationsResponseDTO.convertToDTO(reservationsEntity);
    }


}
