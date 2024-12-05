package project.shop1.domain.order.service;

import project.shop1.domain.order.common.AddressPairs;
import project.shop1.domain.order.dto.*;

import java.util.List;

public interface OrderService {

    // 주문 생성
    OrderResponseDto createOrder(OrderRequestDto orderRequestDto);

    // 주문 상세 조회
    public OrderResponseDto getOrderDetails(Long orderId);

    // 주문 취소
    void cancelOrder(Long orderId);

    // 주문 상태 변경
    OrderResponseDto updateOrderStatus(Long orderId, OrderStatusUpdateRequestDto statusRequestDto);

    // 주문에 상품 추가
    OrderResponseDto addProductToOrder(Long orderId, OrderItemRequestDto productRequest);

    // 주문에서 상품 제거
    OrderResponseDto removeProductFromOrder(Long orderId, Long productId);

    // 특정 사용자의 주문 목록 조회
    List<OrderResponseDto> getOrderList(Long userId);

    // 주소 검색
    List<AddressPairs> searchAddress(SearchAddressRequestDto searchAddressRequestDto);

    // 주소 저장
    void saveAddress(SaveAddressRequestDto saveAddressRequestDto);
}
