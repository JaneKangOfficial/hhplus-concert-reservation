package io.hhplus.concert.presentation.dto.request;

public class PointRequestDTO {

    private Long userId;

    private Integer point;

    private String status;

    public PointRequestDTO(Long userId, Integer point, String status) {
        this.userId = userId;
        this.point = point;
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
