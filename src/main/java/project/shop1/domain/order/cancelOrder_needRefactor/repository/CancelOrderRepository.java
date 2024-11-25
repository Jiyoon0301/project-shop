package project.shop1.domain.order.cancelOrder_needRefactor.repository;

public interface CancelOrderRepository {

    /* 주문 취소 */
    void orderCancel(Long orderId);
    }
