package project.shop1.domain.order.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import project.shop1.domain.order.dto.OrderResponseDto;
import project.shop1.domain.order.dto.OrderStatusUpdateRequestDto;
import project.shop1.domain.order.entity.Order;
import project.shop1.domain.order.entity.OrderItem;
import project.shop1.domain.order.enums.OrderStatus;
import project.shop1.domain.order.repository.OrderRepository;
import project.shop1.domain.product.entity.Book;
import project.shop1.domain.user.entity.UserEntity;
import project.shop1.global.exception.BusinessException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class) //Junit5
public class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Test
    void 주문_상태_업데이트_성공() {
        // Given
        Long orderId = 1L;
        OrderStatusUpdateRequestDto requestDto = new OrderStatusUpdateRequestDto(OrderStatus.DELIVERING);

        UserEntity user = new UserEntity();
        user.setId(1L);

        Order order = Order.builder()
                .id(orderId)
                .orderStatus(OrderStatus.PENDING)
                .userEntity(user)
                .orderItems(List.of(
                        OrderItem.builder()
                                .book(new Book("Test Product", 100))
                                .quantity(2)
                                .orderPrice(200)
                                .build()
                ))
                .totalPrice(200)
                .orderDate(LocalDateTime.now())
                .build();

        Mockito.when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // When
        OrderResponseDto result = orderService.updateOrderStatus(orderId, requestDto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getOrderStatus()).isEqualTo(OrderStatus.DELIVERING);
        Mockito.verify(orderRepository).findById(orderId);
    }

    @Test
    void 주문_ID가_유효하지_않으면_예외_발생() {
        // Given
        Long orderId = 1L;
        OrderStatusUpdateRequestDto requestDto = new OrderStatusUpdateRequestDto(OrderStatus.DELIVERING);

        Mockito.when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> orderService.updateOrderStatus(orderId, requestDto))
                .isInstanceOf(BusinessException.class)
                .hasMessage("존재하지 않는 주문 ID입니다.");
    }
}
