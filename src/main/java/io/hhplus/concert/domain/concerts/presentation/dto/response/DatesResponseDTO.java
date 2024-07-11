package io.hhplus.concert.domain.concerts.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.hhplus.concert.domain.concerts.infrastructure.entity.DatesEntity;
import lombok.Data;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DatesResponseDTO {

    private Long concertOptionId;

    private Long concertId;

    private LocalDate concertDate;

    public DatesResponseDTO(Long concertOptionId, Long concertId, LocalDate concertDate) {
        this.concertOptionId = concertOptionId;
        this.concertId = concertId;
        this.concertDate = concertDate;
    }


    public static DatesResponseDTO convertToDTO(DatesEntity datesEntity) {
        return new DatesResponseDTO(datesEntity.getConcertOptionId(), datesEntity.getConcertId(), datesEntity.getConcertDate());
    }

}
