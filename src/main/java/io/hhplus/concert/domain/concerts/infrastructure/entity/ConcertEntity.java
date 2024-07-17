package io.hhplus.concert.domain.concerts.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "concert")
@NoArgsConstructor
@Data
public class ConcertEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("콘서트 PK")
    private Long id;

    @Comment("콘서트 명")
    private String title;

    @Comment("콘서트 가격")
    private Long price;

    public ConcertEntity(String title, Long price) {
        this.title = title;
        this.price = price;
    }
}
