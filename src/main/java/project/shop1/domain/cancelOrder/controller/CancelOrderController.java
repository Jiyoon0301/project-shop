package project.shop1.domain.cancelOrder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.global.util.reponse.BooleanResponse;
import project.shop1.domain.cancelOrder.dto.CancelOrderRequestDto;
import project.shop1.domain.cancelOrder.service.CancelOrderService;

@RestController
@RequiredArgsConstructor
public class CancelOrderController {

    private final CancelOrderService cancelOrderService;

    /* 주문 취소 */
    @PostMapping("/cancel-order") //CancelOrderRequestDto : Long orderId
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BooleanResponse> cancelOrder(@RequestBody CancelOrderRequestDto cancelOrderRequestDto) {
        cancelOrderService.cancelOrder(cancelOrderRequestDto);
        return ResponseEntity.ok(BooleanResponse.of(true));
    }
}
