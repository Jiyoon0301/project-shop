package project.shop1.feature.cancelOrder.service;

import project.shop1.feature.cancelOrder.dto.CancelOrderRequestDto;

public interface CancelOrderService {

    /* 주문 취소 */
    void cancelOrder(CancelOrderRequestDto cancelOrderRequestDto);

    }
