package project.shop1.feature.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.feature.admin.dto.viewOrderDto.AdminSearchOrderByUserAccountRequestDto;
import project.shop1.feature.admin.dto.viewOrderDto.AdminSearchOrderByUserAccountResponseDto;
import project.shop1.feature.admin.dto.viewOrderDto.AdminSearchAllOrderResponseDto;
import project.shop1.feature.admin.service.ViewOrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class viewOrderController {

    private final ViewOrderService viewOrderService;

    /* 주문 현황 리스트 전체 조회 */
    @PostMapping("/admin/search-all-order-status")
    @PreAuthorize("hasRole('ADMIN')")
    public List<AdminSearchAllOrderResponseDto> adminSearchAllOrder(){
        List<AdminSearchAllOrderResponseDto> result = viewOrderService.adminSearchAllOrder();
        return result;
    }

    /* 주문 기능 추가하기
    * AdminSearchOrderStatusByUserAccountRequestDto :
    * String account
    */
    @PostMapping("/admin/search-order-by-user-account")
    @PreAuthorize("hasRole('ADMIN')")
    public List<AdminSearchOrderByUserAccountResponseDto> adminSearchOrderByUserAccount(@RequestBody AdminSearchOrderByUserAccountRequestDto adminSearchOrderByUserAccountRequestDto){
        List<AdminSearchOrderByUserAccountResponseDto> result = viewOrderService.adminSearchOrderByUserAccount(adminSearchOrderByUserAccountRequestDto);

        return result;
    }


}
