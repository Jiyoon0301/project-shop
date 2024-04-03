package project.shop1.feature.order.service;

import jakarta.servlet.http.HttpServletRequest;
import project.shop1.feature.order.common.AddressPairs;
import project.shop1.feature.order.dto.OrderPageRequestDto;
import project.shop1.feature.order.dto.OrderPageResponseDto;
import project.shop1.feature.order.dto.SaveAddressRequestDto;
import project.shop1.feature.order.dto.SearchAddressRequestDto;

import java.util.List;

public interface OrderService {

    /* 주문 페이지 */
    OrderPageResponseDto orderPage(OrderPageRequestDto orderPageRequestDto, HttpServletRequest request);

    /* 주소 검색 */
    List<AddressPairs> searchAddress(SearchAddressRequestDto searchAddressRequestDto);

    /* 주소 저장 */
    void saveAddress(SaveAddressRequestDto saveAddressRequestDto, HttpServletRequest request);



    }
