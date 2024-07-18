package io.hhplus.concert.domain.points.business.exception;

import io.hhplus.concert.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum PointsException implements ErrorCode {

    LOW_CHARGE("충전 금액은 0보다 작거나 같을 수 없습니다.", HttpStatus.BAD_REQUEST);

    private final String message;
    
    private final HttpStatus httpStatus;

    PointsException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
