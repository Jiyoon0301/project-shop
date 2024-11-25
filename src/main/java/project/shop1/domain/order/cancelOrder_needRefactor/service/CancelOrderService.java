package project.shop1.domain.order.cancelOrder_needRefactor.service;

import project.shop1.domain.order.cancelOrder_needRefactor.dto.CancelOrderRequestDto;

public interface CancelOrderService {

    /* 주문 취소 */
    void cancelOrder(CancelOrderRequestDto cancelOrderRequestDto);

    }
