package project.shop1.feature.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.feature.admin.dto.viewOrderDto.SearchOrderByAccountRequestDto;
import project.shop1.feature.admin.dto.viewOrderDto.SearchOrderByAccountResponseDto;
import project.shop1.feature.admin.dto.viewOrderDto.ViewOrderResponseDto;
import project.shop1.feature.admin.service.ViewOrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class viewOrderController {

    private final ViewOrderService viewOrderService;

    /* 주문 현황 리스트 조회 */
    @PostMapping("/admin/view-order")
    public List<ViewOrderResponseDto> viewOrder(){
        List<ViewOrderResponseDto> result = viewOrderService.viewOrder();
        return result;
    }

    /* 주문 기능 추가하기 */
    @PostMapping("/admin/search-order-by-account")
    public List<SearchOrderByAccountResponseDto> viewOrder(@RequestBody SearchOrderByAccountRequestDto searchOrderByAccountRequestDto){
        List<SearchOrderByAccountResponseDto> result = viewOrderService.searchOrderByAccount(searchOrderByAccountRequestDto);

        return result;
    }


}
