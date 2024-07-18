package io.hhplus.concert.domain.concerts.business.exception;

import io.hhplus.concert.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ConcertsException implements ErrorCode {

    EMPTY_CONCERT("콘서트가 없습니다.", HttpStatus.NOT_FOUND);

    private final String message;

    private final HttpStatus httpStatus;

    ConcertsException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

}
