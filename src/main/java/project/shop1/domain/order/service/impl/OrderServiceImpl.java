package project.shop1.domain.order.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import project.shop1.domain.product.repository.ProductRepository;
import project.shop1.global.exception.BusinessException;
import project.shop1.global.exception.ErrorCode;
import project.shop1.domain.cart.entity.CartItem;
import project.shop1.domain.order.entity.Delivery;
import project.shop1.domain.order.entity.Order;
import project.shop1.domain.order.entity.OrderItem;
import project.shop1.domain.product.entity.Book;
import project.shop1.domain.user.repository.UserRepository;
import project.shop1.global.security.SecurityUtils;
import project.shop1.domain.user.entity.UserEntity;
import project.shop1.domain.order.enums.DeliveryStatus;
import project.shop1.domain.order.enums.OrderStatus;
import project.shop1.domain.cart.repository.CartRepository;
import project.shop1.domain.order.common.AddressPairs;
import project.shop1.domain.order.common.ProductInfoPairs;
import project.shop1.domain.order.dto.*;
import project.shop1.domain.order.repository.OrderRepository;
import project.shop1.domain.order.service.OrderService;

import java.net.URI;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    //특정 사용자의 주문 목록 조회
    public List<OrderResponseDto> getOrderList(Long userId) {
        // 사용자 조회
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "해당 사용자를 찾을 수 없습니다."));

        // 사용자에 대한 주문 조회
        List<Order> orders = orderRepository.findByUserId(userId);

        // Order 엔티티 리스트를 OrderResponseDto 리스트로 변환
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderResponseDto.class))
                .collect(Collectors.toList());
    }

    // 주소 검색
    @Override
    public List<AddressPairs> searchAddress(SearchAddressRequestDto searchAddressRequestDto) {
        String keyword = searchAddressRequestDto.getKeyword();
        int pageNumber = searchAddressRequestDto.getPageNumber();

        URI uri = UriComponentsBuilder
                .fromUriString("https://business.juso.go.kr")
                .path("/addrlink/addrLinkApi.do")
                .queryParam("keyword", keyword)
                .queryParam("confmKey", "devU01TX0FVVEgyMDI0MDkxMzA0MDE1NzExNTA4NTg=")
                .queryParam("currentPage", pageNumber)
                .queryParam("countPerPage", 10)
                .queryParam("resultType", "json")
                .encode(Charset.forName("UTF-8"))
                .build()
                .toUri();

        log.info("uri : {}", uri);

        RestTemplate restTemplate = new RestTemplate();

        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .build();

        ResponseEntity<String> fullAddress = restTemplate.exchange(req, String.class);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(fullAddress.getBody());
            JsonNode jusoNode = rootNode.path("results").path("juso");
            List<AddressPairs> addressPairs = new ArrayList<>();

            for (JsonNode node : jusoNode) {
                String roadAddrPart1 = node.path("roadAddrPart1").asText();
                String jibunAddr = node.path("jibunAddr").asText();

                addressPairs.add(new AddressPairs(roadAddrPart1, jibunAddr));
            }
            return addressPairs;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCode.RESOURCE_ACCESS_NOT_ACCEPTABLE, "fail");
        }
    }

    // 주소 저장
    @Override
    @Transactional
    public void saveAddress(SaveAddressRequestDto saveAddressRequestDto) {
        String account = SecurityUtils.getCurrentUsername();

        String roadAddress = saveAddressRequestDto.getRoadAddress();
        String detailedAddress = saveAddressRequestDto.getDetailedAddress();

        orderRepository.saveAddress(account, roadAddress, detailedAddress);
    }
}
