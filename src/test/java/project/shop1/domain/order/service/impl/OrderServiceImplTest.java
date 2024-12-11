package project.shop1.domain.order.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import project.shop1.domain.cart.dto.request.CartItemRequestDto;
import project.shop1.domain.cart.entity.Cart;
import project.shop1.domain.cart.entity.CartItem;
import project.shop1.domain.order.dto.request.OrderItemRequestDto;
import project.shop1.domain.order.dto.request.OrderRequestDto;
import project.shop1.domain.order.dto.response.OrderResponseDto;
import project.shop1.domain.order.dto.request.OrderStatusUpdateRequestDto;
import project.shop1.domain.order.entity.Order;
import project.shop1.domain.order.entity.OrderItem;
import project.shop1.domain.order.enums.OrderStatus;
import project.shop1.domain.order.repository.OrderRepository;
import project.shop1.domain.product.entity.Book;
import project.shop1.domain.product.repository.ProductRepository;
import project.shop1.domain.user.entity.UserEntity;
import project.shop1.domain.user.repository.UserRepository;
import project.shop1.global.exception.BusinessException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class) //Junit5
public class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    private OrderItem orderItem;
    private UserEntity user;
    private Book product;
    private Order order;

    @BeforeEach
    void setUp() {
        user = UserEntity.builder()
                .id(1L)
                .name("Test User")
                .build();

        product = Book.builder()
                .id(1L)
                .title("Test Book")
                .price(100)
                .build();

        orderItem = OrderItem.builder()
                .book(product)
                .quantity(2)
                .orderPrice(200)
                .build();

        order = Order.builder()
                .id(1L)
                .orderStatus(OrderStatus.PENDING)
                .userEntity(user)
                .orderItems(List.of(orderItem))
                .totalPrice(100)
                .orderDate(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("createOrder: 주문_생성_성공")
    void 주문_생성_성공() {
        // given
        Long userId = 1L;
        Long productId = 1L;

        OrderRequestDto orderRequestDto = OrderRequestDto.builder()
                .userId(userId)
                .orderItems(List.of(
                        OrderRequestDto.OrderItemRequestDto.builder()
                                .productId(productId)
                                .quantity(2)
                                .build()
                ))
                .build();

        OrderResponseDto.OrderItemDto expectedItemDto = new OrderResponseDto.OrderItemDto(
                productId, "Test Book", 2, 200
        );

        Order mockOrder = Order.builder()
                .id(1L)
                .userEntity(user)
                .orderItems(List.of(orderItem))
                .totalPrice(200)
                .orderStatus(OrderStatus.PENDING)
                .orderDate(LocalDateTime.now())
                .build();

        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(productRepository.findById(productId)).willReturn(Optional.of(product));
        given(orderRepository.save(any(Order.class))).willReturn(mockOrder);

        // when
        OrderResponseDto result = orderService.createOrder(orderRequestDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(user.getId());
        assertThat(result.getTotalPrice()).isEqualTo(mockOrder.getTotalPrice());
        assertThat(result.getOrderStatus()).isEqualTo(OrderStatus.PENDING);
        assertThat(result.getOrderItems()).hasSize(1);
        assertThat(result.getOrderItems().get(0))
                .usingRecursiveComparison()
                .isEqualTo(expectedItemDto);

        verify(userRepository, times(1)).findById(userId);
        verify(productRepository, times(1)).findById(productId);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    @DisplayName("updateOrderStatus: 주문_상태_업데이트_성공")
    void 주문_상태_업데이트_성공() {
        // given
        Long orderId = 1L;
        OrderStatusUpdateRequestDto requestDto = new OrderStatusUpdateRequestDto(OrderStatus.DELIVERING);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // when
        OrderResponseDto result = orderService.updateOrderStatus(orderId, requestDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getOrderStatus()).isEqualTo(OrderStatus.DELIVERING);
        verify(orderRepository).findById(orderId);
    }

    @Test
    @DisplayName("updateOrderStatus: 주문_ID가_유효하지_않으면_예외_발생")
    void 주문_ID가_유효하지_않으면_예외_발생() {
        // given
        Long orderId = 1L;
        OrderStatusUpdateRequestDto requestDto = new OrderStatusUpdateRequestDto(OrderStatus.DELIVERING);

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // when / then
        assertThatThrownBy(() -> orderService.updateOrderStatus(orderId, requestDto))
                .isInstanceOf(BusinessException.class)
                .hasMessage("존재하지 않는 주문 ID입니다.");
    }

    @Test
    @DisplayName("updateOrderStatus: 완료된_주문의_상태를_변경하려고_하면_예외_발생")
    void 완료된_주문의_상태를_변경하려고_하면_예외_발생() {
        // given
        Long orderId = 1L;
        OrderStatusUpdateRequestDto requestDto = new OrderStatusUpdateRequestDto(OrderStatus.CANCEL);

        UserEntity user = new UserEntity();
        user.setId(1L);

        Order order = Order.builder()
                .id(orderId)
                .orderStatus(OrderStatus.COMPLETED)
                .userEntity(user)
                .orderItems(new ArrayList<>())
                .build();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // when / then
        assertThatThrownBy(() -> orderService.updateOrderStatus(orderId, requestDto))
                .isInstanceOf(BusinessException.class)
                .hasMessage("완료된 주문은 상태를 변경할 수 없습니다.");
    }

    @Test
    @DisplayName("addProductToOrder: 주문에 상품 추가 성공")
    void 주문에_상품_추가_성공_테스트() {
        // given
        Long orderId = 1L;
        Long productId = 2L;

        Order order = new Order();
        order.setId(orderId);

        Book product = new Book();
        product.setId(productId);
        product.setPrice(1000);

        OrderItemRequestDto request = new OrderItemRequestDto(productId, 3);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // when
        OrderResponseDto response = orderService.addProductToOrder(orderId, request);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getOrderId()).isEqualTo(orderId);
        assertThat(response.getOrderItems()).hasSize(1);

        OrderResponseDto.OrderItemDto addedProduct = response.getOrderItems().get(0);
        assertThat(addedProduct.getProductId()).isEqualTo(productId);
        assertThat(addedProduct.getQuantity()).isEqualTo(3);
    }

    @Test
    @DisplayName("removeProductFromOrder: 주문에서 상품 제거 성공")
    void removeProductFromOrder_SuccessTest() {
        // given
        Long orderId = 1L;
        Long productId = 1L;

        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setBook(product);
        orderItem.setQuantity(2);

        Order order = new Order();
        order.setId(orderId);
        order.setOrderItems(new ArrayList<>(List.of(orderItem)));

        Mockito.when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // when
        OrderResponseDto response = orderService.removeProductFromOrder(orderId, productId);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getOrderId()).isEqualTo(orderId);
        assertThat(response.getOrderItems()).isEmpty();

        verify(orderRepository, times(1)).findById(orderId);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    @DisplayName("removeProductFromOrder: 존재하지 않는 주문이면 예외 발생")
    void removeProductFromOrder_OrderNotFound() {
        // given
        Long orderId = 1L;
        Long productId = 100L;

        Mockito.when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> orderService.removeProductFromOrder(orderId, productId))
                .isInstanceOf(BusinessException.class)
                .hasMessage("존재하지 않는 주문입니다.");

        Mockito.verify(orderRepository, times(1)).findById(orderId);
        Mockito.verify(orderRepository, Mockito.never()).save(any());
    }

    @Test
    @DisplayName("removeProductFromOrder: 존재하지 않는 상품이면 예외 발생")
    void removeProductFromOrder_ProductNotFound() {
        // given
        Long orderId = 1L;
        Long productId = 100L;

        Order order = new Order();
        order.setId(orderId);
        order.setOrderItems(new ArrayList<>());

        Mockito.when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // when & then
        assertThatThrownBy(() -> orderService.removeProductFromOrder(orderId, productId))
                .isInstanceOf(BusinessException.class)
                .hasMessage("존재하지 않는 상품입니다.");

        Mockito.verify(orderRepository, times(1)).findById(orderId);
        Mockito.verify(orderRepository, Mockito.never()).save(any());
    }
}
