package io.hhplus.concert.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.logging.LogLevel;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;
    private final Object data;
    private final LogLevel logLevel;

    public CustomException(ErrorCode errorCode, LogLevel logLevel, Object data) {
        this.errorCode = errorCode;
        this.logLevel = logLevel;
        this.data = data;
    }

}