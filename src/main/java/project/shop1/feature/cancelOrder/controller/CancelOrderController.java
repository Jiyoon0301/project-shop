package project.shop1.feature.cancelOrder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.common.reponse.BooleanResponse;
import project.shop1.feature.cancelOrder.dto.CancelOrderRequestDto;
import project.shop1.feature.cancelOrder.service.CancelOrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CancelOrderController {

    private final CancelOrderService cancelOrderService;

    /* 주문 취소 */
    @PostMapping("/cancel-order") //CancelOrderRequestDto : Long orderId
    public ResponseEntity<BooleanResponse> cancelOrder(@RequestBody CancelOrderRequestDto cancelOrderRequestDto) {
        cancelOrderService.cancelOrder(cancelOrderRequestDto);
        return ResponseEntity.ok(BooleanResponse.of(true));
    }
}
