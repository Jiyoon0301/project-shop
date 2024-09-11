package project.shop1.feature.myPage.getUserOrders.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.common.security.SecurityUtil;
import project.shop1.entity.enums.DeliveryStatus;
import project.shop1.entity.Order;
import project.shop1.entity.OrderItem;
import project.shop1.entity.enums.OrderStatus;
import project.shop1.feature.myPage.getUserOrders.common.OrderItemsPairs;
import project.shop1.feature.myPage.getUserOrders.dto.GetUserOrdersResponseDto;
import project.shop1.feature.myPage.getUserOrders.repository.GetUserOrdersRepository;
import project.shop1.feature.myPage.getUserOrders.service.GetUserOrdersService;
import project.shop1.feature.order.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetUserOrdersServiceImpl implements GetUserOrdersService {

    private final GetUserOrdersRepository getUserOrdersRepository;
    private final OrderRepository orderRepository;

    /* 회원의 주문 내역 확인 버튼 */
    @Override
    @Transactional
    public List<GetUserOrdersResponseDto> getUserOrders(){
        String account = SecurityUtil.getCurrentUsername();

        List<Order> orderList = orderRepository.findOrdersByUserEntityAccount(account);

        List<GetUserOrdersResponseDto> result = new ArrayList<>();

        for (Order order : orderList){

            List<OrderItemsPairs> orderItemsPairs = new ArrayList<>();
            for (OrderItem orderItem : order.getOrderItems()){
                OrderItemsPairs pairs = new OrderItemsPairs(orderItem.getBook().getTitle(), orderItem.getOrderPrice(), orderItem.getCount());
                orderItemsPairs.add(pairs);
            }

            GetUserOrdersResponseDto dto = GetUserOrdersResponseDto.builder()
                    .orderdate(order.getOrderDate().toLocalDate())
                    .orderItemsPairs(orderItemsPairs)
                    .build();
            if (order.getOrderStatus() == OrderStatus.CANCEL){
                dto.setOrderStatus(OrderStatus.CANCEL);
            } else {
                dto.setDeliveryStatus(DeliveryStatus.COMPLETE);
            }
            result.add(dto);
        }

        return result;
    }
}
