package io.hhplus.concert.domain.concerts.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "concert_option")
@NoArgsConstructor
@Data
public class DatesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long concertOptionId;

    @Column(name = "concert_id")
    private Long concertId;

    @Column(name = "concert_date")
    private LocalDate concertDate;

    public DatesEntity(Long concertOptionId, Long concertId, LocalDate concertDate) {
        this.concertOptionId = concertOptionId;
        this.concertId = concertId;
        this.concertDate = concertDate;
    }
}
