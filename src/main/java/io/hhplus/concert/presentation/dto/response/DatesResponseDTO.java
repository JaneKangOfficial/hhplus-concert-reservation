package io.hhplus.concert.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DatesResponseDTO {

    private List<Long> dateId;

    private Long concertId;

    private List<LocalDate> availableDates;


    public DatesResponseDTO(List<Long> dateId, Long concertId, List<LocalDate> availableDates) {
        this.dateId = dateId;
        this.concertId = concertId;
        this.availableDates = availableDates;
    }

    public DatesResponseDTO(List<LocalDate> availableDates) {
        this.availableDates = availableDates;
    }

    public List<Long> getDateId() {
        return dateId;
    }

    public void setDateId(List<Long> dateId) {
        this.dateId = dateId;
    }

    public Long getConcertId() {
        return concertId;
    }

    public void setConcertId(Long concertId) {
        this.concertId = concertId;
    }

    public List<LocalDate> getAvailableDates() {
        return availableDates;
    }

    public void setAvailableDates(List<LocalDate> availableDates) {
        this.availableDates = availableDates;
    }
}
