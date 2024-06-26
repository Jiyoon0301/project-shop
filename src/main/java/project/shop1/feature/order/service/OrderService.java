package project.shop1.feature.order.service;

import jakarta.servlet.http.HttpServletRequest;
import project.shop1.entity.Order;
import project.shop1.entity.OrderItem;
import project.shop1.feature.order.common.AddressPairs;
import project.shop1.feature.order.dto.*;

import java.util.List;

public interface OrderService {

    /* 주문 페이지 */
    OrderPageResponseDto orderPage(OrderPageRequestDto orderPageRequestDto);

    /* 주문(구매하기 버튼) */
    SubmitOrderResponseDto submitOrder(SubmitOrderRequestDto submitOrderRequestDto);

    /* 카트 목록으로 orderItems 리스트 생성 */
    List<OrderItem> createOrderItemList(List<Long> cartItemsId);

    /* 장바구니 목록 총 가격 */
    int calTotalPrice(List<OrderItem> orderItemList);




        /* 주소 검색 */
    List<AddressPairs> searchAddress(SearchAddressRequestDto searchAddressRequestDto);

    /* 주소 저장 */
    void saveAddress(SaveAddressRequestDto saveAddressRequestDto);



    }
