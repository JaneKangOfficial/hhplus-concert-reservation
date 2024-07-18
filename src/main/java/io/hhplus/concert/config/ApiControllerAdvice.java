package io.hhplus.concert.config;

import io.hhplus.concert.common.dto.ApiResponse;
import io.hhplus.concert.common.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @ ControllerAdvice
 * 컨트롤러에서 발생하는 예외를 전역적으로 처리하기 위해 사용
 * 모든 컨트롤러에 걸쳐 공통적으로 적용
 **/

@ControllerAdvice
class ApiControllerAdvice {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    // 내가 정의한 Exception 이 발생했을 때 에러 응답
    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<ApiResponse<Object>> handleGlobalException(CustomException e) {
//    public ResponseEntity<Object> handleGlobalException(CustomException e) {
        switch (e.getLogLevel()) {
            case ERROR -> logger.error("ApiException : {}", e.getErrorCode().getMessage(), e.getData(), e);
            case WARN -> logger.warn("ApiException : {}", e.getErrorCode().getMessage(), e.getData(), e);
            default -> logger.info("ApiException : {}", e.getErrorCode().getMessage(), e.getData(), e);
        }

        ApiResponse<Object> apiResponse = new ApiResponse<>(
                e.getData(),
//                e.getErrorCode().getHttpStatus().toString(),
                200, // request는 문제가 없으니까 200
                e.getErrorCode().getMessage()
        );
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(apiResponse);
//        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(e.getErrorCode().getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public void handleRuntimeException(RuntimeException e) {
        logger.error(e.getMessage(), e);
    }

/*
    // 허재 코치님의 참고 코드
    // 내가 정의한 Exception 이 발생했을 때 에러 응답
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<?>> handleCustomException(CustomException e) {
        switch (e.getLogLevel()) {
            case ERROR -> logger.error("ApiException : {}", e.getMessage(), e);
            case WARN -> logger.warn("ApiException : {}", e.getMessage(), e);
            default -> logger.info("ApiException : {}", e.getMessage(), e);
        }
        // Http status 200 선호 "UserNotFound --> x 200"
        return new ResponseEntity<>(ApiResponse.error(e.getMessage(), e.getData()), getErrorType(e).getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        log.error("UnhandledException : {}", e.getMessage(), e);
        return new ResponseEntity<>(ApiResponse.error(e.getMessage()), DEFAULT_ERROR.getStatus());
    }
*/


}