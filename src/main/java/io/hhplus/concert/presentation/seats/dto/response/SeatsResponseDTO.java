package io.hhplus.concert.presentation.seats.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SeatsResponseDTO {

    private Long concertId;

    private Long dateId;

    private List<Long> seatId;


    public SeatsResponseDTO(Long concertId, Long dateId, List<Long> seatId) {
        this.concertId = concertId;
        this.dateId = dateId;
        this.seatId = seatId;
    }

    public SeatsResponseDTO(List<Long> seatId) {
        this.seatId = seatId;
    }

    public Long getConcertId() {
        return concertId;
    }

    public void setConcertId(Long concertId) {
        this.concertId = concertId;
    }

    public Long getDateId() {
        return dateId;
    }

    public void setDate(Long dateId) {
        this.dateId = dateId;
    }

    public List<Long> getSeatId() {
        return seatId;
    }

    public void setSeatId(List<Long> seatId) {
        this.seatId = seatId;
    }
}
