package io.hhplus.concert.common.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    String getMessage();

    HttpStatus getHttpStatus();

}