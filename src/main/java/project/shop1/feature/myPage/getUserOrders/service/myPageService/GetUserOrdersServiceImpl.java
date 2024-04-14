package project.shop1.feature.myPage.getUserOrders.service.myPageService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.entity.DeliveryStatus;
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
    public List<GetUserOrdersResponseDto> getUserOrders(HttpServletRequest request){
        HttpSession session = request.getSession();
        String account = (String) session.getAttribute("account"); // 세션에 저장된 사용자 정보

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
