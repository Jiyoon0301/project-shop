package project.shop1.domain.cancelOrder.service;

import project.shop1.domain.cancelOrder.dto.CancelOrderRequestDto;

public interface CancelOrderService {

    /* 주문 취소 */
    void cancelOrder(CancelOrderRequestDto cancelOrderRequestDto);

    }
