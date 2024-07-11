package io.hhplus.concert.domain.concerts.infrastructure.entity;

import io.hhplus.concert.domain.concerts.business.entity.SeatsStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "seat")
@Data
@NoArgsConstructor
public class SeatsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "concert_option_id", referencedColumnName = "id")
    private DatesEntity concertOption;

    @Column(name = "num")
    private Long num;

    @Enumerated(EnumType.STRING)
    private SeatsStatus status;

    @Column(name = "lock_until")
    private LocalDateTime lockUntil;

    public SeatsEntity(Long id, DatesEntity concertOption, Long num, SeatsStatus status) {
        this.id = id;
        this.concertOption = concertOption;
        this.num = num;
        this.status = status;
    }
}
