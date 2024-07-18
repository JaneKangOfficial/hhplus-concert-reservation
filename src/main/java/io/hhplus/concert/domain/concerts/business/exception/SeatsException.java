package io.hhplus.concert.domain.concerts.business.exception;

import io.hhplus.concert.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SeatsException implements ErrorCode {

    EMPTY_SEATS("예약 가능한 좌석이 없습니다.", HttpStatus.NOT_FOUND);

    private final String message;

    private final HttpStatus httpStatus;

    SeatsException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
