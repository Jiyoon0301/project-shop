package project.shop1.feature.admin.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.entity.Order;
import project.shop1.entity.enums.OrderStatus;
import project.shop1.feature.admin.dto.viewOrderDto.SearchOrderByAccountRequestDto;
import project.shop1.feature.admin.dto.viewOrderDto.SearchOrderByAccountResponseDto;
import project.shop1.feature.admin.dto.viewOrderDto.ViewOrderResponseDto;
import project.shop1.feature.admin.repository.ViewOrderRepository;
import project.shop1.feature.admin.service.ViewOrderService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ViewOrderServiceImpl implements ViewOrderService {

    private final ViewOrderRepository viewOrderRepository;

    /* 주문 현황 리스트 조회 */
    @Override
    public List<ViewOrderResponseDto> viewOrder(){
        List<Order> findAllOrder = viewOrderRepository.findAllOrderList();

        List<ViewOrderResponseDto> result = new ArrayList<>();

        for (Order order : findAllOrder){
            Boolean cancelled = false;
            if (order.getOrderStatus().equals(OrderStatus.CANCEL)){
                cancelled = true;}
            ViewOrderResponseDto dto = new ViewOrderResponseDto(order.getId(), order.getUserEntity().getAccount(), order.getOrderDate().toLocalDate(), order.getDelivery().getStatus(), cancelled);
            result.add(dto);
        }

        return result;
    }

    /* userEntity account로 주문 리스트 조회 */
    @Override
    public List<SearchOrderByAccountResponseDto> searchOrderByAccount(SearchOrderByAccountRequestDto searchOrderByAccountRequestDto){
        String account = searchOrderByAccountRequestDto.getAccount();
        List<Order> findOrder = viewOrderRepository.findOrderByUserEntityAccount(account);

        List<SearchOrderByAccountResponseDto> result = new ArrayList<>();

        for (Order order : findOrder){
            Boolean cancelled = false;
            if (order.getOrderStatus().equals(OrderStatus.CANCEL)){
                cancelled = true;}
            SearchOrderByAccountResponseDto dto = new SearchOrderByAccountResponseDto(order.getId(), order.getUserEntity().getAccount(), order.getOrderDate().toLocalDate(), order.getDelivery().getStatus(), cancelled);
            result.add(dto);
        }

        return result;
    }
}
