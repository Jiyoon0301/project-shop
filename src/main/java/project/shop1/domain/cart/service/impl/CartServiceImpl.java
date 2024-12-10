package project.shop1.domain.cart.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.domain.cart.dto.request.AddProductRequestDto;
import project.shop1.domain.cart.dto.request.CartItemRequestDto;
import project.shop1.domain.cart.dto.request.CartItemUpdateRequestDto;
import project.shop1.domain.cart.dto.response.CartItemResponseDto;
import project.shop1.domain.cart.dto.response.CartResponseDto;
import project.shop1.domain.cart.dto.response.OrderItemResponseDto;
import project.shop1.domain.cart.dto.response.OrderResponseDto;
import project.shop1.domain.cart.entity.Cart;
import project.shop1.domain.cart.entity.CartItem;
import project.shop1.domain.order.entity.Order;
import project.shop1.domain.order.entity.OrderItem;
import project.shop1.domain.order.repository.OrderRepository;
import project.shop1.domain.product.entity.Book;
import project.shop1.domain.product.repository.ProductRepository;
import project.shop1.domain.user.entity.UserEntity;
import project.shop1.domain.user.repository.UserRepository;
import project.shop1.global.exception.BusinessException;
import project.shop1.global.exception.ErrorCode;
import project.shop1.domain.cart.repository.CartRepository;
import project.shop1.domain.cart.service.CartService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String CART_KEY = "cart:";

    // 장바구니에 상품 추가
    @Override
    @Transactional
    public CartItemResponseDto addItemToCart(Long cartId, CartItemRequestDto request) {
        // 장바구니 존재 여부 확인
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "장바구니를 찾을 수 없습니다."));

        // 상품 존재 여부 확인
        Book product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "상품을 찾을 수 없습니다."));

        // 장바구니에 동일 상품이 있는지 확인
        Optional<CartItem> existingCartItem = cart.getItems().stream()
                .filter(item -> item.getBook().getId().equals(product.getId()))
                .findFirst();

        CartItem cartItem;
        if (existingCartItem.isPresent()) {
            cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
        } else {
            cartItem = new CartItem(cart, product, request.getQuantity(), product.getPrice());
            cart.addItem(cartItem);
        }

        cartRepository.save(cart);

        return CartItemResponseDto.builder()
                .itemId(cartItem.getId())
                .productId(product.getId())
                .title(cartItem.getBook().getTitle())
                .quantity(cartItem.getQuantity())
                .price(cartItem.getPrice())
                .totalPrice(cartItem.getPrice() * cartItem.getQuantity())
                .build();
    }

    // 장바구니에 새로운 상품 추가
    @Override
    @Transactional
    public CartResponseDto addProductToCart(Long userId, AddProductRequestDto request) {
        // 사용자에 대한 장바구니 찾기 (장바구니가 없다면 새로 생성)
        Cart cart = cartRepository.findByUserEntity_Id(userId)
                .orElseGet(() -> createNewCartForUser(userId));

        // 상품 존재 여부 확인
        Book product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "상품을 찾을 수 없습니다."));

        // 장바구니에 상품 추가
        CartItem cartItem = new CartItem(cart, product, request.getQuantity(), product.getPrice());
        cart.addItem(cartItem);  // 상품 추가

        // 장바구니 저장
        cartRepository.save(cart);

        // CartResponseDto 반환
        return new CartResponseDto(cart);
    }

    // 새로운 장바구니 생성
    private Cart createNewCartForUser(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "사용자를 찾을 수 없습니다."));

        Cart newCart = Cart.builder()
                .userEntity(userEntity)
                .build();

        cartRepository.save(newCart);

        return newCart;
    }

    // 장바구니 상품 조회
    @Override
    public List<CartItemResponseDto> getCartItems(Long cartId) {
        String redisKey = CART_KEY + cartId;

        // 먼저 Redis에서 장바구니 조회
        Cart cachedCart = (Cart) redisTemplate.opsForValue().get(redisKey);

        if (cachedCart == null) {
            // Redis에 장바구니가 없으면 DB에서 조회
            Cart cart = cartRepository.findById(cartId)
                    .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "장바구니를 찾을 수 없습니다."));

            // 장바구니를 Redis에 캐시
            redisTemplate.opsForValue().set(redisKey, cart);

            cachedCart = cart;
        }

        // 장바구니 항목 가져오기 및 DTO 변환
        List<CartItemResponseDto> responseDtos = new ArrayList<>();
        for (CartItem cartItem : cachedCart.getItems()) {
            CartItemResponseDto dto = new CartItemResponseDto();
            dto.setItemId(cartItem.getId());
            dto.setProductId(cartItem.getBook().getId());
            dto.setTitle(cartItem.getBook().getTitle()); // 상품 이름이 `title`이라고 가정
            dto.setQuantity(cartItem.getQuantity());
            dto.setPrice(cartItem.getPrice());
            dto.setTotalPrice(cartItem.getPrice() * cartItem.getQuantity()); // 총 가격 계산
            responseDtos.add(dto);
        }

        return responseDtos;
    }

    @Override
    public CartResponseDto getCartByUserId(Long userId) {
        // 사용자 ID로 장바구니 조회
        Cart cart = cartRepository.findByUserEntity_Id(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "해당 사용자의 장바구니를 찾을 수 없습니다."));

        // 장바구니 항목 변환
        List<CartItemResponseDto> items = cart.getItems().stream()
                .map(cartItem -> CartItemResponseDto.builder()
                        .itemId(cartItem.getId())
                        .productId(cartItem.getBook().getId())
                        .title(cartItem.getBook().getTitle())
                        .quantity(cartItem.getQuantity())
                        .price(cartItem.getPrice())
                        .totalPrice(cartItem.getPrice() * cartItem.getQuantity())
                        .build()
                )
                .toList();

        // 장바구니 데이터 변환
        return CartResponseDto.builder()
                .id(cart.getId())
                .userId(cart.getUserEntity().getId())
                .items(items)
                .build();
    }

    // 장바구니 상품 업데이트
    @Override
    public CartItemResponseDto updateCartItem(Long cartId, Long itemId, CartItemUpdateRequestDto request) {
        String redisKey = CART_KEY + cartId;

        // 장바구니 확인
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "장바구니를 찾을 수 없습니다."));

        // 장바구니 아이템 확인
        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "장바구니 아이템을 찾을 수 없습니다."));

        // 장바구니 아이템 업데이트
        cartItem.setQuantity(request.getQuantity());
        cartItem.setPrice(request.getPrice()); // 가격 업데이트가 필요하다고 가정

        // 장바구니 저장
        cartRepository.save(cart);

        // Redis 캐시 업데이트
        redisTemplate.opsForValue().set(redisKey, cart);

        // 응답 DTO 변환
        return CartItemResponseDto.builder()
                .itemId(cartItem.getId())
                .productId(cartItem.getBook().getId())
                .title(cartItem.getBook().getTitle())
                .quantity(cartItem.getQuantity())
                .price(cartItem.getPrice())
                .totalPrice(cartItem.getPrice() * cartItem.getQuantity())
                .build();
    }

    // 장바구니 상품 수량 업데이트
    @Override
    public CartItemResponseDto updateQuantity(Long cartId, Long itemId, int quantity) {
        // 장바구니 확인
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "장바구니를 찾을 수 없습니다."));

        // 장바구니 아이템 확인
        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "장바구니 아이템을 찾을 수 없습니다."));

        // 수량 업데이트
        cartItem.setQuantity(quantity);

        // 저장
        cartRepository.save(cart);

        // 응답 DTO 변환
        return CartItemResponseDto.builder()
                .itemId(cartItem.getId())
                .productId(cartItem.getBook().getId())
                .title(cartItem.getBook().getTitle())
                .quantity(cartItem.getQuantity())
                .price(cartItem.getPrice())
                .totalPrice(cartItem.getPrice() * cartItem.getQuantity())
                .build();
    }

    // 장바구니에서 상품 삭제
    @Override
    public void removeItemFromCart(Long cartId, Long itemId) {
        // 장바구니 확인
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "장바구니를 찾을 수 없습니다."));

        // 장바구니 아이템 확인
        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "장바구니 아이템을 찾을 수 없습니다."));

        // 장바구니에서 아이템 제거
        cart.getItems().remove(cartItem);

        // 장바구니 저장
        cartRepository.save(cart);
    }

    // 장바구니 초기화
    @Override
    public void clearCart(Long cartId) {
        // 장바구니 확인
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "장바구니를 찾을 수 없습니다."));

        // 장바구니 비우기
        cart.getItems().clear();

        // 장바구니 저장
        cartRepository.save(cart);
    }

    // 장바구니 내용을 주문으로 변환
    @Override
    @Transactional
    public OrderResponseDto convertCartToOrder(Long cartId) {
        // 장바구니 확인
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "장바구니를 찾을 수 없습니다."));

        // 장바구니가 비어있는지 확인
        if (cart.getItems().isEmpty()) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "장바구니가 비어있습니다.");
        }

        // 주문 생성
        Order order = createOrder(cart);

        // 주문 저장
        orderRepository.save(order);

        // 장바구니 비우기
        cart.getItems().clear();
        cartRepository.save(cart);

        // 응답 DTO 변환
        return OrderResponseDto.builder()
                .orderId(order.getId())
                .userId(order.getUserEntity().getId())
                .orderDate(order.getOrderDate())
                .totalPrice(order.getTotalPrice())
                .items(order.getOrderItems().stream()
                        .map(orderItem -> OrderItemResponseDto.builder()
                                .productId(orderItem.getBook().getId())
                                .title(orderItem.getBook().getTitle())
                                .quantity(orderItem.getQuantity())
                                .price(orderItem.getOrderPrice())
                                .totalPrice(orderItem.getOrderPrice() * orderItem.getQuantity())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    private Order createOrder(Cart cart) {
        return Order.builder()
                .userEntity(cart.getUserEntity()) // 장바구니의 사용자 정보
                .orderDate(LocalDateTime.now()) // 현재 시간
                .totalPrice(cart.getItems().stream()
                        .mapToInt(item -> item.getPrice() * item.getQuantity())
                        .sum()) // 총 가격 계산
                .orderItems(cart.getItems().stream()
                        .map(cartItem -> OrderItem.builder()
                                .book(cartItem.getBook())
                                .quantity(cartItem.getQuantity())
                                .orderPrice(cartItem.getPrice())
                                .build())
                        .collect(Collectors.toList())) // 장바구니 항목을 주문 항목으로 변환
                .build();
    }
}
