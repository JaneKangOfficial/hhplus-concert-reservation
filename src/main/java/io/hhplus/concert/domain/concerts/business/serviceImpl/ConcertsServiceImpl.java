package io.hhplus.concert.domain.concerts.business.serviceImpl;

import io.hhplus.concert.common.status.SeatsStatus;
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
import io.hhplus.concert.common.status.ReservationsStatus;
import io.hhplus.concert.domain.reservations.business.repository.ReservationsRepository;
import io.hhplus.concert.domain.reservations.infrastructure.entity.ReservationsEntity;
import io.hhplus.concert.domain.reservations.presentation.dto.request.ReservationsRequestDTO;
import io.hhplus.concert.domain.reservations.presentation.dto.response.ReservationsResponseDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConcertsServiceImpl implements ConcertsService {

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
    public List<ConcertsResponseDTO> getConcerts() {
        // 콘서트 목록 가져오기
        List<ConcertEntity> concertEntityList = concertsRepository.findAll();

        return concertEntityList.stream()
                .map(ConcertsResponseDTO::convertToDTO)
                .collect(Collectors.toList());
    }


    // 예약 가능 날짜 조회
    @Override
    public List<DatesResponseDTO> getDates(Long concertId) {


        List<DatesEntity> datesEntityList = datesRepository.findByConcertId(concertId);

        return datesEntityList.stream()
                .map(DatesResponseDTO::convertToDTO)
                .collect(Collectors.toList());
    }

    // 예약 가능 좌석 조회
    @Override
    public List<SeatsResponseDTO> getSeats(Long concertId, Long dateId) {
        // 5분 지난 좌석 잠금 해제
        checkSeatsExpiration();

        List<Long> seatNumList = seatsRepository.findSeatNumbersByConcertIdAndDateId(concertId, dateId);
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
    public ReservationsResponseDTO reservations(ReservationsRequestDTO reservationsRequestDTO) {
        ReservationsEntity reservationsEntity = new ReservationsEntity();

        // 선택한 좌석이 예약 가능하면 예약 진행
        Optional<SeatsEntity> seatsEntity = seatsRepository.findById(reservationsRequestDTO.getSeatId());

        if (seatsEntity.isPresent() && seatsEntity.get().getStatus() == SeatsStatus.AVAILABLE) {
            // 선택한 좌석 잠금
            seatsRepository.updateStatusAndLockuntil(reservationsRequestDTO.getSeatId(), SeatsStatus.UNAVAILABLE, currentDateTime.plusMinutes(5));

            // 예약
            reservationsRequestDTO.setStatus(ReservationsStatus.APPLY);
            reservationsEntity = reservationsRepository.save(ReservationsRequestDTO.convertToEntity(reservationsRequestDTO));

        } else {
            throw new NullPointerException();
        }
        return ReservationsResponseDTO.convertToDTO(reservationsEntity);
    }


}
