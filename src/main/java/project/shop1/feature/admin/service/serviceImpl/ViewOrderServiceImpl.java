package project.shop1.feature.admin.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.entity.Order;
import project.shop1.entity.enums.OrderStatus;
import project.shop1.feature.admin.dto.viewOrderDto.AdminSearchOrderByUserAccountRequestDto;
import project.shop1.feature.admin.dto.viewOrderDto.AdminSearchOrderByUserAccountResponseDto;
import project.shop1.feature.admin.dto.viewOrderDto.AdminSearchAllOrderResponseDto;
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
    public List<AdminSearchAllOrderResponseDto> adminSearchAllOrder(){
        List<Order> AllOrder = viewOrderRepository.findAllOrderList();

        List<AdminSearchAllOrderResponseDto> result = new ArrayList<>();

        for (Order order : AllOrder){
            Boolean cancelled = false;
            if (order.getOrderStatus().equals(OrderStatus.CANCEL)){
                cancelled = true;}
            AdminSearchAllOrderResponseDto dto = new AdminSearchAllOrderResponseDto(order.getId(), order.getUserEntity().getAccount(), order.getOrderDate().toLocalDate(), order.getDelivery().getStatus(), cancelled);
            result.add(dto);
        }

        return result;
    }

    /* userEntity account 로 주문 리스트 조회 */
    @Override
    public List<AdminSearchOrderByUserAccountResponseDto> adminSearchOrderByUserAccount(AdminSearchOrderByUserAccountRequestDto adminSearchOrderByUserAccountRequestDto){
        String account = adminSearchOrderByUserAccountRequestDto.getAccount();
        List<Order> findOrder = viewOrderRepository.findOrderByUserEntityAccount(account);

        List<AdminSearchOrderByUserAccountResponseDto> result = new ArrayList<>();

        for (Order order : findOrder){
            Boolean cancelled = false;
            if (order.getOrderStatus().equals(OrderStatus.CANCEL)){
                cancelled = true;}
            AdminSearchOrderByUserAccountResponseDto dto = new AdminSearchOrderByUserAccountResponseDto(order.getId(), order.getUserEntity().getAccount(), order.getOrderDate().toLocalDate(), order.getDelivery().getStatus(), cancelled);
            result.add(dto);
        }

        return result;
    }
}
