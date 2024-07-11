package io.hhplus.concert.domain.concerts.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.hhplus.concert.domain.concerts.infrastructure.entity.SeatsEntity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SeatsResponseDTO {

    private Long seatNum;

    public SeatsResponseDTO(Long seatNum) {
        this.seatNum = seatNum;
    }

    public Long getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(Long seatNum) {
        this.seatNum = seatNum;
    }

    public static SeatsResponseDTO convertToDTO(SeatsEntity seatsEntity) {
        return new SeatsResponseDTO(seatsEntity.getNum());
    }


}
