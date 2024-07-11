package io.hhplus.concert.domain.reservations.business.entity;

/**
 * 예약 상태
 * PENDING : 보류
 * APPLY : 요청
 * CANCEL : 취소
 **/

public enum ReservationsStatus {
    PENDING,
    APPLY,
    CANCEL
}