package io.hhplus.concert.common.status;

/**
 * 좌석 예약 상태
 * PENDING : 보류
 * COMPLETE : 완료
 * EXPIRED : 만료
 **/
public enum SeatReservationsStatus {
    PENDING,
    COMPLETE,
    EXPIRED;

    /**
     * 토큰 상태
     * ACTIVE : 활성
     * EXPIRATION : 만료
     **/

    public enum TokensStatus {
        ACTIVE,
        EXPIRATION

    }
}
