package project.shop1.feature.admin.service;


import project.shop1.feature.admin.dto.viewOrderDto.AdminSearchOrderByUserAccountRequestDto;
import project.shop1.feature.admin.dto.viewOrderDto.AdminSearchOrderByUserAccountResponseDto;
import project.shop1.feature.admin.dto.viewOrderDto.AdminSearchAllOrderResponseDto;

import java.util.List;

public interface ViewOrderService {

    /* 주문 현황 리스트 조회 */
    List<AdminSearchAllOrderResponseDto> adminSearchAllOrder();

    /* userEntity account로 주문 리스트 조회 */
    List<AdminSearchOrderByUserAccountResponseDto> adminSearchOrderByUserAccount(AdminSearchOrderByUserAccountRequestDto adminSearchOrderByUserAccountRequestDto);

    }
