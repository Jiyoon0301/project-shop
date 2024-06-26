package project.shop1.feature.myPage.getUserOrders.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.common.security.SecurityUtil;
import project.shop1.feature.myPage.getUserOrders.dto.GetUserOrdersResponseDto;
import project.shop1.feature.myPage.getUserOrders.service.GetUserOrdersService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetUserOrdersController {

    private final GetUserOrdersService getUserOrdersService;

    /* 회원의 주문 내역 확인 버튼 - 오래된 날짜부터 */
    @PostMapping("/my-page/get-user-orders")
    @PreAuthorize("hasRole('USER')")
    public List<GetUserOrdersResponseDto> findAllCartItemsByUser() {

        List<GetUserOrdersResponseDto> result = getUserOrdersService.getUserOrders();

        return result;
    }
}
