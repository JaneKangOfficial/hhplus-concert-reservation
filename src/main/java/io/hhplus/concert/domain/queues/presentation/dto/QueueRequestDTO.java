package io.hhplus.concert.domain.queues.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueueRequestDTO {
    String userId;
    String key;
}
