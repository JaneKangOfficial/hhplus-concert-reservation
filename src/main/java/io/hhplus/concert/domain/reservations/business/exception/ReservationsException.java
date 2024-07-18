package io.hhplus.concert.domain.reservations.business.exception;

import io.hhplus.concert.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ReservationsException implements ErrorCode {

    UNAVAILABLE_RESERVATION("이용할 수 없는 예약입니다.", HttpStatus.NOT_FOUND);

    private final String message;

    private final HttpStatus httpStatus;

    ReservationsException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

}
