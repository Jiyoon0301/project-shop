package project.shop1.domain.order.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.domain.order.dto.request.*;
import project.shop1.domain.order.dto.response.OrderResponseDto;
import project.shop1.domain.product.repository.ProductRepository;
import project.shop1.global.exception.BusinessException;
import project.shop1.global.exception.ErrorCode;
import project.shop1.domain.order.entity.Order;
import project.shop1.domain.order.entity.OrderItem;
import project.shop1.domain.product.entity.Book;
import project.shop1.domain.user.repository.UserRepository;
import project.shop1.domain.user.entity.UserEntity;
import project.shop1.domain.order.enums.OrderStatus;
import project.shop1.domain.cart.repository.CartRepository;
import project.shop1.domain.order.repository.OrderRepository;
import project.shop1.domain.order.service.OrderService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper; // Entity-DTO 변환용

    // 주문 생성
    @Override
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        // 사용자 조회
        UserEntity userEntity = userRepository.findById(orderRequestDto.getUserId())
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "존재하지 않는 사용자입니다."));

        // 주문 상품 검증 및 조회
        List<OrderItem> orderItems = orderRequestDto.getOrderItems().stream()
                .map(itemDto -> {
                    Book product = productRepository.findById(itemDto.getProductId())
                            .orElseThrow(() -> new IllegalArgumentException("Product not found"));

                    return OrderItem.builder()
                            .book(product)
                            .quantity(itemDto.getQuantity())
                            .orderPrice(product.getPrice() * itemDto.getQuantity())
                            .build();
                })
                .collect(Collectors.toList());

        // 주문 총액 계산
        int totalPrice = orderItems.stream()
                .mapToInt(OrderItem::getOrderPrice)
                .sum();

        // 주문 생성
        Order order = Order.builder()
                .userEntity(userEntity)
                .orderItems(orderItems)
                .totalPrice(totalPrice)
                .orderStatus(OrderStatus.PENDING) // 기본 상태 설정
                .orderDate(LocalDateTime.now())
                .build();

        // 저장
        orderRepository.save(order);

        // Response DTO로 변환 및 반환
        return OrderResponseDto.builder()
                .orderId(order.getId())
                .userId(userEntity.getId())
                .totalPrice(order.getTotalPrice())
                .orderStatus(order.getOrderStatus())
                .orderItems(orderItems.stream()
                        .map(item -> new OrderResponseDto.OrderItemDto(
                                item.getBook().getId(),
                                item.getBook().getTitle(),
                                item.getQuantity(),
                                item.getOrderPrice()))
                        .collect(Collectors.toList()))
                .orderDate(order.getOrderDate())
                .build();
    }

    // 주문 상세 정보 조회
    @Override
    public OrderResponseDto getOrderDetails(Long orderId) {
        // orderId로 주문 조회
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "해당 주문을 찾을 수 없습니다."));

        // Order 엔티티를 OrderResponseDto로 변환 후 반환
        return modelMapper.map(order, OrderResponseDto.class);
    }

    // 특정 사용자의 주문 목록 조회
    public List<OrderResponseDto> getOrderList(Long userId) {
        // 사용자 조회
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "해당 사용자를 찾을 수 없습니다."));

        // 사용자에 대한 주문 조회
        List<Order> orders = orderRepository.findByUserEntity_Id(userId);

        // Order 엔티티 리스트를 OrderResponseDto 리스트로 변환
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderResponseDto.class))
                .collect(Collectors.toList());
    }

    // 주문 취소
    @Override
    @Transactional
    public void cancelOrder(Long orderId) {
        // 주문 조회
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "존재하지 않는 주문입니다."));

        // 주문 상태 확인
        if (order.getOrderStatus() == OrderStatus.CANCEL) {
            throw new BusinessException(ErrorCode.RESOURCE_CONFLICT, "처리 중이거나 완료된 주문은 취소할 수 없습니다.");
        }

        // 주문 상태 변경
        order.setOrderStatus(OrderStatus.CANCEL);

        // 저장
        orderRepository.save(order);
    }

    // 주문 상태 변경
    @Transactional
    @Override
    public OrderResponseDto updateOrderStatus(Long orderId, OrderStatusUpdateRequestDto statusRequestDto) {
        // 주문 조회
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "존재하지 않는 주문 ID입니다."));

        // 주문 상태 업데이트
        OrderStatus newStatus = statusRequestDto.getStatus();
        if (newStatus == null) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "유효하지 않은 주문 상태입니다.");
        }

        // 예외 처리: 이미 완료된 주문은 상태를 변경할 수 없음
        if (order.getOrderStatus() == OrderStatus.COMPLETED) {
            throw new BusinessException(ErrorCode.RESOURCE_ACCESS_NOT_ACCEPTABLE, "완료된 주문은 상태를 변경할 수 없습니다.");
        }

        order.setOrderStatus(newStatus);

        // 변경된 주문 정보를 DTO로 변환하여 반환
        return OrderResponseDto.builder()
                .orderId(order.getId())
                .orderStatus(order.getOrderStatus())
                .userId(order.getUserEntity().getId())
                .orderItems(order.getOrderItems().stream()
                        .map(item -> OrderResponseDto.OrderItemDto.builder()
                                .productId(item.getBook().getId())
                                .quantity(item.getQuantity())
                                .price(item.getOrderPrice())
                                .build())
                        .toList())
                .totalPrice(order.getTotalPrice())
                .orderDate(order.getOrderDate())
                .build();
    }

    // 주문에 상품 추가
    @Transactional
    @Override
    public OrderResponseDto addProductToOrder(Long orderId, OrderItemRequestDto productRequest) {
        // 주문 존재 여부 확인
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + orderId));

        // 상품 존재 여부 확인
        Book product = productRepository.findById(productRequest.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productRequest.getProductId()));

        // 주문에 상품 추가
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setBook(product);
        orderItem.setQuantity(productRequest.getQuantity());

        order.addOrderItem(orderItem);

        // 주문 저장 (Cascade 설정으로 연관된 OrderProduct도 저장)
        orderRepository.save(order);

        // 응답 DTO 생성
        return OrderResponseDto.builder()
                .orderId(order.getId())
                .orderItems(order.getOrderItems().stream()
                        .map(item -> OrderResponseDto.OrderItemDto.builder()
                                .productId(item.getBook().getId())
                                .quantity(item.getQuantity())
                                .price(item.getOrderPrice())
                                .build())
                        .toList())
                .build();
    }

    // 주문에서 상품 제거
    @Override
    @Transactional
    public OrderResponseDto removeProductFromOrder(Long orderId, Long productId) {
        // 주문 조회
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "존재하지 않는 주문입니다."));

        // 주문에서 해당 상품 제거
        OrderItem orderProductToRemove = order.getOrderItems().stream()
                .filter(orderProduct -> orderProduct.getBook().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "존재하지 않는 상품입니다."));

        order.getOrderItems().remove(orderProductToRemove);

        // 저장 (변경 감지로 영속성 컨텍스트가 업데이트됨)
        orderRepository.save(order);

        // 응답 DTO 생성
        List<OrderResponseDto.OrderItemDto> itemDtos = order.getOrderItems().stream()
                .map(oi -> OrderResponseDto.OrderItemDto.builder()
                        .productId(oi.getBook().getId())
                        .quantity(oi.getQuantity())
                        .build())
                .collect(Collectors.toList());

        return OrderResponseDto.builder()
                .orderId(order.getId())
                .orderItems(itemDtos)
                .build();
    }
}
