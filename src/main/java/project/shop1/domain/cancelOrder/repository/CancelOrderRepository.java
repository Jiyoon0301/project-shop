package project.shop1.domain.cancelOrder.repository;

public interface CancelOrderRepository {

    /* 주문 취소 */
    void orderCancel(Long orderId);
    }
