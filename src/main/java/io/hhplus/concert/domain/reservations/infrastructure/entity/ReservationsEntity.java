package io.hhplus.concert.domain.reservations.infrastructure.entity;

import io.hhplus.concert.domain.reservations.business.entity.ReservationsStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "seat_reservation")
public class ReservationsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "concert_id")
    private Long concertId;

    @Column(name = "date_id")
    private Long dateId;

    @Column(name = "seat_id")
    private Long seatId;

    @Enumerated(EnumType.STRING)
    private ReservationsStatus status;

    public ReservationsEntity(Long userId, Long concertId, Long dateId, Long seatId, ReservationsStatus status) {
        this.userId = userId;
        this.concertId = concertId;
        this.dateId = dateId;
        this.seatId = seatId;
        this.status = status;
    }

}
