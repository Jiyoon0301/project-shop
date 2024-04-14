package project.shop1.feature.myPage.getUserOrders.service;

import jakarta.servlet.http.HttpServletRequest;
import project.shop1.feature.myPage.getUserOrders.dto.GetUserOrdersResponseDto;

import java.util.List;

public interface GetUserOrdersService {

    /* 회원의 주문 내역 확인 버튼 */
    List<GetUserOrdersResponseDto> getUserOrders(HttpServletRequest request);

    }
