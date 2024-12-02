package project.shop1.domain.order.enums;

public enum OrderStatus {
    PENDING,     // 주문 대기 중
    ORDER,  // 주문 처리 중
    DELIVERING, // 배송 중
    COMPLETED,   // 모든 처리 완료
    CANCEL  // 주문 취소
}
