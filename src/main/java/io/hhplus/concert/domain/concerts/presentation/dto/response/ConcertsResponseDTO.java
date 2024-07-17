package io.hhplus.concert.domain.concerts.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.hhplus.concert.domain.concerts.infrastructure.entity.ConcertEntity;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ConcertsResponseDTO {
    private Long id;
    private String title;
    private Long price;

    public ConcertsResponseDTO(Long id, String title, Long price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    public static ConcertsResponseDTO convertToDTO(ConcertEntity concertEntity) {
        return new ConcertsResponseDTO(concertEntity.getId(), concertEntity.getTitle(), concertEntity.getPrice());
    }
}
