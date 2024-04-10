package project.shop1.feature.admin.service;


import project.shop1.feature.admin.dto.viewOrderDto.SearchOrderByAccountRequestDto;
import project.shop1.feature.admin.dto.viewOrderDto.SearchOrderByAccountResponseDto;
import project.shop1.feature.admin.dto.viewOrderDto.ViewOrderResponseDto;

import java.util.List;

public interface ViewOrderService {

    /* 주문 현황 리스트 조회 */
    List<ViewOrderResponseDto> viewOrder();

    /* userEntity account로 주문 리스트 조회 */
    List<SearchOrderByAccountResponseDto> searchOrderByAccount(SearchOrderByAccountRequestDto searchOrderByAccountRequestDto);

    }
