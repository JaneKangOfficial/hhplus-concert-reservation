package io.hhplus.concert.common.dto;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private Object data;
    private Integer success;
    private String message;

    public ApiResponse(Object data, Integer success, String message) {
        this.data = data;
        this.success = success;
        this.message = message;
    }

    public static <T> ApiResponse<T> error(Object data, Integer success, String message) {
        return new ApiResponse<>(data, success, message);
    }

}
