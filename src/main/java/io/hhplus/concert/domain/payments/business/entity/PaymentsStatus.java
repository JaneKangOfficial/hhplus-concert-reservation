package io.hhplus.concert.domain.payments.business.entity;

/**
 * 결제 상태
 * PENDING : 보류
 * COMPLETE : 완료
 * CANCEL : 취소
 **/

public enum PaymentsStatus {
    PENDING,
    COMPLETE,
    CANCEL
}