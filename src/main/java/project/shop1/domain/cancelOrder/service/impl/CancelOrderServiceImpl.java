package project.shop1.domain.cancelOrder.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.domain.cancelOrder.dto.CancelOrderRequestDto;
import project.shop1.domain.cancelOrder.repository.CancelOrderRepository;
import project.shop1.domain.cancelOrder.service.CancelOrderService;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CancelOrderServiceImpl implements CancelOrderService {

    private final CancelOrderRepository cancelOrderRepository;

    /* 주문취소 */
    @Override
    @Transactional
    public void cancelOrder(CancelOrderRequestDto cancelOrderRequestDto) {
        Long orderId = cancelOrderRequestDto.getOrderId();
        cancelOrderRepository.orderCancel(orderId);

    }
}
