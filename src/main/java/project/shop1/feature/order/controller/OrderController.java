package project.shop1.feature.order.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.common.reponse.BooleanResponse;
import project.shop1.feature.order.common.AddressPairs;
import project.shop1.feature.order.dto.*;
import project.shop1.feature.order.service.OrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /* 주문 페이지 */ // orderByCart, orderByProductInfo
    @PostMapping("/users/{userEntityId}/orders") // OrderRequestDto : Boolean isFromCartPage, Long bookId, int count;
    @PreAuthorize("hasRole('USER')")
    public OrderPageResponseDto order (@PathVariable("userEntityId") Long userEntityId, @RequestBody OrderPageRequestDto orderPageRequestDto){
        OrderPageResponseDto orderPageResponseDto = orderService.orderPage(orderPageRequestDto);
        return orderPageResponseDto;
    }

    /* 주문 페이지에서 구매하기 버튼 */
    @PostMapping("/order/order-submitOrder-{userEntityId}") // SubmitOrderRequestDto : Boolean isFromCartPage, Long CartItemId, String address, Long bookid, int count
    @PreAuthorize("hasRole('USER')")
    public SubmitOrderResponseDto submitOrder (@PathVariable("userEntityId") Long userEntityId, @RequestBody SubmitOrderRequestDto submitOrderRequestDto){
        SubmitOrderResponseDto result= orderService.submitOrder(submitOrderRequestDto);

        return result;
    }

    /* 사용자 주문 상세 조회 기능 추가 */

    /* 배송지 입력을 위한 조회 */
    @PostMapping("/order/search-address") // String keyword, int pageNumber
    @PreAuthorize("hasRole('USER')")
    public List<AddressPairs> searchAddress(@RequestBody SearchAddressRequestDto searchAddressRequestDto) {
        List<AddressPairs> result = orderService.searchAddress(searchAddressRequestDto);
        return result;
    }

    /* 주소 저장 - 도로명 + 상세주소 저장 */
    @PostMapping("/order/save-address") //SaveAddressRequestDto : String roadAddress, String detailedAddress
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BooleanResponse> saveAddress(@RequestBody SaveAddressRequestDto saveAddressRequestDto) {
        orderService.saveAddress(saveAddressRequestDto);

        return ResponseEntity.ok(BooleanResponse.of(true));
    }

}
