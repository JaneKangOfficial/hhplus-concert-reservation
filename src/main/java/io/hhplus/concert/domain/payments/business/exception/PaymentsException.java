package io.hhplus.concert.domain.payments.business.exception;


import io.hhplus.concert.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum PaymentsException implements ErrorCode {

    LACK_OF_POINT("잔액이 부족합니다.", HttpStatus.PAYMENT_REQUIRED);

    private final String message;
    
    private final HttpStatus httpStatus;

    PaymentsException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

}
