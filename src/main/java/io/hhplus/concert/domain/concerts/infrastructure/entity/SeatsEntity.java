package io.hhplus.concert.domain.concerts.infrastructure.entity;

import io.hhplus.concert.common.status.SeatsStatus;
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
    @Column(name = "seat_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "concert_option_id")
    private DatesEntity concertOption;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "num")
    private Long num;

    @Enumerated(EnumType.STRING)
    private SeatsStatus status;

    @Column(name = "lock_until")
    private LocalDateTime lockUntil;

    @Version    // 낙관적 락
    private Long version;

    public SeatsEntity(Long id, DatesEntity concertOption, Long num, SeatsStatus status) {
        this.id = id;
        this.concertOption = concertOption;
        this.num = num;
        this.status = status;
    }
}
