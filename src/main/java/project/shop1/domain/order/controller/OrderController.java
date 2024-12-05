package project.shop1.domain.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.shop1.global.util.reponse.BooleanResponse;
import project.shop1.domain.order.common.AddressPairs;
import project.shop1.domain.order.dto.*;
import project.shop1.domain.order.service.OrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    /**
     * 주문 생성
     * @param orderRequestDto
     * @return
     */
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        OrderResponseDto createdOrderDto = orderService.createOrder(orderRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderDto);
    }

    /**
     * 주문 상세 조회
     * @param orderId
     * @return
     */
    @GetMapping("/{orderId}")
    @PreAuthorize("hasRole('USER') and #orderId == authentication.principal.id") // 자신의 주문만 접근 가능
    public ResponseEntity<OrderResponseDto> getOrderDetails(@PathVariable Long orderId) {
        OrderResponseDto orderDetails = orderService.getOrderDetails(orderId);
        return ResponseEntity.ok(orderDetails);
    }

    /**
     * 주문 목록 조회
     * @param userId
     * @return
     */
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<OrderResponseDto>> getOrderList(@RequestParam Long userId) {
        List<OrderResponseDto> orders = orderService.getOrderList(userId);
        return ResponseEntity.ok(orders);
    }

    /**
     * 주문 취소
     * @param orderId
     * @return
     */
    @DeleteMapping("/{orderId}")
    @PreAuthorize("hasRole('USER') and #orderId == authentication.principal.id")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 주문 상태 변경
     * @param orderId
     * @param statusRequestDto
     * @return
     */
    @PatchMapping("/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderResponseDto> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestBody OrderStatusUpdateRequestDto statusRequestDto) {
        OrderResponseDto updatedOrder = orderService.updateOrderStatus(orderId, statusRequestDto);
        return ResponseEntity.ok(updatedOrder);
    }

    /**
     * 주문에 상품 추가
     * @param orderId
     * @param productRequest
     * @return
     */
    @PostMapping("/{orderId}/products")
    @PreAuthorize("hasRole('USER') and #orderId == authentication.principal.id")
    public ResponseEntity<OrderResponseDto> addProductToOrder(
            @PathVariable Long orderId,
            @RequestBody OrderItemRequestDto productRequest) {
        OrderResponseDto updatedOrder = orderService.addProductToOrder(orderId, productRequest);
        return ResponseEntity.ok(updatedOrder);
    }

    /**
     * 주문에서 상품 제거
     * @param orderId
     * @param productId
     * @return
     */
    @DeleteMapping("/{orderId}/products/{productId}")
    @PreAuthorize("hasRole('USER') and #orderId == authentication.principal.id")
    public ResponseEntity<OrderResponseDto> removeProductFromOrder(
            @PathVariable Long orderId,
            @PathVariable Long productId) {
        OrderResponseDto updatedOrderDto= orderService.removeProductFromOrder(orderId, productId);
        return ResponseEntity.ok(updatedOrderDto);
    }

//     결제
//    @PostMapping("/{orderId}/payment")
//    @PreAuthorize("hasRole('USER')")
//    public ResponseEntity<PaymentResponse> handlePayment(
//            @PathVariable Long orderId,
//            @RequestBody PaymentRequest paymentRequest) {
//        PaymentResponse paymentResponse = orderService.handlePayment(orderId, paymentRequest);
//        return ResponseEntity.ok(paymentResponse);
//    }

    /**
     * 주소 조회 ****************
     * @param searchAddressRequestDto
     * @return
     */
    @PostMapping("/search-address") // String keyword, int pageNumber
    @PreAuthorize("hasRole('USER')")
    public List<AddressPairs> searchAddress(@RequestBody SearchAddressRequestDto searchAddressRequestDto) {
        List<AddressPairs> result = orderService.searchAddress(searchAddressRequestDto);
        return result;
    }

    /**
     * 주소 저장 - 도로명 + 상세주소
     * @param saveAddressRequestDto
     * @return
     */
    @PostMapping("/save-address") //SaveAddressRequestDto : String roadAddress, String detailedAddress
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BooleanResponse> saveAddress(@RequestBody SaveAddressRequestDto saveAddressRequestDto) {
        orderService.saveAddress(saveAddressRequestDto);

        return ResponseEntity.ok(BooleanResponse.of(true));
    }

}
