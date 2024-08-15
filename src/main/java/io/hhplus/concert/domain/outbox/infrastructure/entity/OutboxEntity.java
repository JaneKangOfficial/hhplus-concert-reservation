package io.hhplus.concert.domain.outbox.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "outbox")
@NoArgsConstructor
@Data
public class OutboxEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message_id")
    private Long messageId;

    private String status;

    private String type;

    private String message;


    public OutboxEntity(Long messageId, String status, String type, String message) {
        this.messageId = messageId;
        this.status = status;
        this.type = type;
        this.message = message;
    }
}
