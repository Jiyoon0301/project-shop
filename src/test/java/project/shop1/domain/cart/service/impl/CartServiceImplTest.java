package project.shop1.domain.cart.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import project.shop1.domain.cart.dto.request.AddProductRequestDto;
import project.shop1.domain.cart.dto.request.CartItemRequestDto;
import project.shop1.domain.cart.dto.request.CartItemUpdateRequestDto;
import project.shop1.domain.cart.dto.response.CartItemResponseDto;
import project.shop1.domain.cart.dto.response.CartResponseDto;
import project.shop1.domain.cart.dto.response.OrderResponseDto;
import project.shop1.domain.cart.entity.Cart;
import project.shop1.domain.cart.entity.CartItem;
import project.shop1.domain.cart.repository.CartRepository;
import project.shop1.domain.order.entity.Order;
import project.shop1.domain.order.repository.OrderRepository;
import project.shop1.domain.product.entity.Book;
import project.shop1.domain.product.repository.ProductRepository;
import project.shop1.domain.user.entity.UserEntity;
import project.shop1.global.exception.BusinessException;
import project.shop1.global.exception.ErrorCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class) //Junit5
public class CartServiceImplTest {

    @InjectMocks
    private CartServiceImpl cartService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    private final String CART_KEY = "cart:";

    // Mock 데이터
    private CartItemRequestDto requestDto;
    private Cart cart;
    private Book product;
    private CartItem cartItem;
    private UserEntity userEntity;
    private Order order;

    @BeforeEach
    void setUp() {
        userEntity = UserEntity.builder()
                .id(1L)
                .name("Test User")
                .build();

        requestDto = CartItemRequestDto.builder()
                .productId(1L) // 상품 Id 설정
                .quantity(2) // 상품 수량 설정
                .build();

        product = Book.builder()
                .id(1L) // 상품 id 설정
                .title("Test Book") // 상품 제목 설정
                .price(100) // 상품 가격 설정
                .build();

        cartItem = CartItem.builder()
                .id(1L)
                .quantity(2)
                .price(200)
                .book(product)
                .build();

        cart = Cart.builder()
                .id(1L)
                .userEntity(userEntity)
                .items(new ArrayList<>(List.of(cartItem)))
                .build();

        order = Order.builder()
                .id(1L)
                .userEntity(cart.getUserEntity())
                .orderDate(LocalDateTime.now())
                .totalPrice(200)
                .orderItems(new ArrayList<>())
                .build();
    }

    @Test
    void addItemToCart_장바구니에_상품_추가_성공() {
        // given
        when(cartRepository.findById(cart.getId())).thenReturn(Optional.of(cart)); // 장바구니 존재 확인
        when(productRepository.findById(requestDto.getProductId())).thenReturn(Optional.of(product)); // 상품 존재 확인
        when(cartRepository.save(any(Cart.class))).thenReturn(cart); // 장바구니 저장

        CartItem cartItem = cart.getItems().get(0);
        cartItem.setQuantity(cartItem.getQuantity() + requestDto.getQuantity());

        CartItemResponseDto expectedResponseDto = CartItemResponseDto.builder()
                .itemId(cartItem.getId())
                .productId(product.getId())
                .title(cartItem.getBook().getTitle())
                .quantity(cartItem.getQuantity())
                .price(cartItem.getPrice())
                .totalPrice(cartItem.getPrice() * cartItem.getQuantity())
                .build();

        when(modelMapper.map(any(CartItem.class), eq(CartItemResponseDto.class))).thenReturn(expectedResponseDto);

        // when
        CartItemResponseDto responseDto = cartService.addItemToCart(cart.getId(), requestDto);

        // then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getProductId()).isEqualTo(product.getId()); // 상품 ID 확인
        assertThat(responseDto.getQuantity()).isEqualTo(cartItem.getQuantity()); // 수량 확인
        assertThat(responseDto.getTotalPrice()).isEqualTo(cartItem.getPrice() * cartItem.getQuantity()); // 총 가격 확인

        verify(cartRepository, times(1)).save(any(Cart.class)); // cartRepository.save()가 1번 호출되었는지 확인
    }

    @Test
    void addItemToCart_장바구니에_상품_추가_실패_장바구니_존재하지_않음() {
        // given
        when(cartRepository.findById(cart.getId())).thenReturn(Optional.empty()); // 장바구니가 없을 경우

        // when & then
        assertThatThrownBy(() -> cartService.addItemToCart(cart.getId(), requestDto))
                .isInstanceOf(BusinessException.class) // 예외가 발생하는지 확인
                .hasMessage("장바구니를 찾을 수 없습니다.");
    }

    @Test
    void addItemToCart_장바구니에_상품_추가_실패_상품_존재하지_않음() {
        // given
        when(cartRepository.findById(cart.getId())).thenReturn(Optional.of(cart)); // 장바구니 존재
        when(productRepository.findById(requestDto.getProductId())).thenReturn(Optional.empty()); // 상품이 없을 경우

        // when & then
        assertThatThrownBy(() -> cartService.addItemToCart(cart.getId(), requestDto))
                .isInstanceOf(BusinessException.class) // 예외가 발생하는지 확인
                .hasMessage("상품을 찾을 수 없습니다.");
    }

    @Test
    void addProductToCart_장바구니에_새로운_상품_추가_성공() {
        // given
        Long userId = 1L;
        AddProductRequestDto requestDto = AddProductRequestDto.builder()
                .productId(1L)
                .quantity(2)
                .build();

        Cart cart = Cart.builder()
                .id(1L)
                .userEntity(userEntity)
                .items(new ArrayList<>())
                .build();

        when(cartRepository.findByUserEntity_Id(userId)).thenReturn(Optional.of(cart));
        when(productRepository.findById(requestDto.getProductId())).thenReturn(Optional.of(product));

        CartItemResponseDto mockResponseDto = new CartItemResponseDto();
        mockResponseDto.setItemId(1L);
        mockResponseDto.setProductId(product.getId());
        mockResponseDto.setQuantity(requestDto.getQuantity());
        mockResponseDto.setTotalPrice(product.getPrice() * requestDto.getQuantity());

        // when
        CartResponseDto responseDto = cartService.addProductToCart(userId, requestDto);

        // then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getId()).isEqualTo(cart.getId());
        assertThat(responseDto.getUserId()).isEqualTo(userId); // userEntity의 id 확인
        assertThat(responseDto.getItems()).hasSize(1);

        CartItemResponseDto cartItemResponseDto = responseDto.getItems().get(0);
        assertThat(cartItemResponseDto.getProductId()).isEqualTo(product.getId());
        assertThat(cartItemResponseDto.getQuantity()).isEqualTo(requestDto.getQuantity());
        assertThat(cartItemResponseDto.getTotalPrice()).isEqualTo(product.getPrice() * requestDto.getQuantity());

        verify(cartRepository, times(1)).findByUserEntity_Id(userId);
        verify(productRepository, times(1)).findById(requestDto.getProductId());
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void getCartItems_장바구니_조회_성공() {
        // given
        Long cartId = 1L;

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        ValueOperations<String, Object> valueOps = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOps);

        // when
        List<CartItemResponseDto> responseDtos = cartService.getCartItems(cartId);

        // then
        assertThat(responseDtos).hasSize(1); // 반환된 항목 개수 확인
        CartItemResponseDto dto = responseDtos.get(0);
        assertThat(dto.getItemId()).isEqualTo(cartItem.getId());
        assertThat(dto.getProductId()).isEqualTo(product.getId());
        assertThat(dto.getTitle()).isEqualTo(product.getTitle());
        assertThat(dto.getQuantity()).isEqualTo(cartItem.getQuantity());
        assertThat(dto.getPrice()).isEqualTo(cartItem.getPrice());
        assertThat(dto.getTotalPrice()).isEqualTo(cartItem.getPrice() * cartItem.getQuantity());

        // verify
        verify(cartRepository, times(1)).findById(cartId);
    }

    @Test
    void getCartItems_존재하지_않는_장바구니_조회_시도하면_예외발생() {
        // given
        Long cartId = 1L;
        when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        ValueOperations<String, Object> valueOps = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOps);

        // when & then
        assertThatThrownBy(() -> cartService.getCartItems(cartId))
                .isInstanceOf(BusinessException.class)
                .hasMessage("장바구니를 찾을 수 없습니다.");

        verify(cartRepository, times(1)).findById(cartId);
    }

    @Test
    void getCartByUserId_사용자_장바구니_조회_성공() {
        // given
        Long userId = 1L;

        when(cartRepository.findByUserEntity_Id(userId)).thenReturn(Optional.of(cart));

        // when
        CartResponseDto response = cartService.getCartByUserId(userId);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(cart.getId());
        assertThat(response.getUserId()).isEqualTo(cart.getUserEntity().getId());
        assertThat(response.getItems()).hasSize(1);
        assertThat(response.getItems().get(0).getItemId()).isEqualTo(cartItem.getId());
        assertThat(response.getItems().get(0).getProductId()).isEqualTo(cartItem.getBook().getId());
        assertThat(response.getItems().get(0).getTitle()).isEqualTo(cartItem.getBook().getTitle());
        assertThat(response.getItems().get(0).getQuantity()).isEqualTo(cartItem.getQuantity());
        assertThat(response.getItems().get(0).getPrice()).isEqualTo(cartItem.getPrice());
        assertThat(response.getItems().get(0).getTotalPrice()).isEqualTo(cartItem.getPrice() * cartItem.getQuantity());

        verify(cartRepository, times(1)).findByUserEntity_Id(userId);
    }

    @Test
    void getCartByUserId_사용자의_존재하지_않는_장바구니를_조회하려고_하면_예외발생() {
        // given
        Long userId = 1L;
        when(cartRepository.findByUserEntity_Id(userId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> cartService.getCartByUserId(userId))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.RESOURCE_NOT_FOUND)
                .hasMessage("해당 사용자의 장바구니를 찾을 수 없습니다.");

        verify(cartRepository, times(1)).findByUserEntity_Id(userId);
    }

    @Test
    void updateCartItem_장바구니_상품_업데이트_성공() {
        // given
        Long cartId = 1L;
        Long itemId = 1L;
        CartItemUpdateRequestDto request = new CartItemUpdateRequestDto(3, 200);
        String redisKey = CART_KEY + cartId;

        ValueOperations<String, Object> valueOps = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOps);
        when(redisTemplate.opsForValue().get(redisKey)).thenReturn(null); // Redis 캐시 미존재

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        // when
        CartItemResponseDto response = cartService.updateCartItem(cartId, itemId, request);

        // then
        assertThat(response.getQuantity()).isEqualTo(3);
        assertThat(response.getPrice()).isEqualTo(200);
        assertThat(response.getTotalPrice()).isEqualTo(600); // 3 * 200 = 600

        verify(redisTemplate.opsForValue()).set(redisKey, cart);
    }

    @Test
    void updateCartItem_장바구니가_존재하지_않을_때_장바구니의_상품을_업데이트_시도하면_예외발생() {
        // given
        Long cartId = 1L;
        Long itemId = 1L;
        CartItemUpdateRequestDto request = new CartItemUpdateRequestDto(3, 200);

        when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> cartService.updateCartItem(cartId, itemId, request))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("장바구니를 찾을 수 없습니다.");
    }

    @Test
    void updateCartItem_장바구니_상품이_존재하지_않을_때_업데이트_시도하면_예외발생() {
        // given
        Long cartId = 1L;
        Long itemId = 1L;
        CartItemUpdateRequestDto request = new CartItemUpdateRequestDto(3, 200);

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        // when & then
        assertThatThrownBy(() -> cartService.updateCartItem(cartId, 999L, request)) // 존재하지 않는 아이템 ID
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("장바구니 아이템을 찾을 수 없습니다.");
    }

    @Test
    void updateCartItem_장바구니_상품_업데이트_저장_성공() {
        // given
        Long cartId = 1L;
        Long itemId = 1L;
        CartItemUpdateRequestDto request = new CartItemUpdateRequestDto(3, 200);
        String redisKey = CART_KEY + cartId;

        ValueOperations<String, Object> valueOps = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOps);
        //1. ValueOperations<String, Object> valueOps = mock(ValueOperations.class);
        //설명: ValueOperations는 Redis에서 값을 읽고 쓰는 작업을 담당하는 인터페이스
        // 이 코드는 ValueOperations 인터페이스를 모킹(mocking)하여 valueOps라는 가짜 객체를 생성
        // 이 가짜 객체는 실제 Redis 서버와 상호작용하지 않고, 테스트에서 정의한 대로 동작
        //목적: Redis에서 값을 가져오거나 저장하는 동작을 모킹하여, 실제 Redis 서버 없이 테스트를 실행할 수 있게 함
        //2. when(redisTemplate.opsForValue()).thenReturn(valueOps);
        //설명: redisTemplate.opsForValue()는 Redis의 ValueOperations 객체를 반환
        // 이 줄은 redisTemplate.opsForValue()가 호출될 때, 앞서 생성한 valueOps 모킹 객체를 반환하도록 설정
        //목적: 테스트에서 redisTemplate.opsForValue()를 호출할 때, 실제 Redis와 상호작용하지 않고 모킹된 valueOps 객체가 반환되도록 하여, 이후 Redis 관련 메서드들을 모킹할 수 있도록 설정
        //3. when(redisTemplate.opsForValue().get(redisKey)).thenReturn(null);
        //설명: 이 줄은 redisTemplate.opsForValue().get(redisKey)가 호출되었을 때, null을 반환하도록 설정
        // 즉, Redis에서 해당 키(redisKey)에 대한 값이 없다는 시나리오를 시뮬레이션
        //목적: 테스트에서 장바구니가 Redis 캐시에 존재하지 않는 상황을 만들어주기 위한 설정
        // 이 설정 덕분에, 장바구니를 DB에서 가져오는 로직이 실행되게 됨

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        // when
        cartService.updateCartItem(cartId, itemId, request);

        // then
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void updateQuantity_장바구니_항목_수량_업데이트_성공() {
        // given
        Long cartId = 1L;
        Long itemId = 1L;
        int quantity = 5;

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        // when
        CartItemResponseDto response = cartService.updateQuantity(cartId, itemId, quantity);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getItemId()).isEqualTo(itemId);
        assertThat(response.getProductId()).isEqualTo(product.getId());
        assertThat(response.getQuantity()).isEqualTo(quantity);
        assertThat(response.getTotalPrice()).isEqualTo(cartItem.getPrice() * quantity);
    }

    @Test
    void updateQuantity_장바구니_없음_예외() {
        // given
        Long cartId = 1L;
        Long itemId = 1L;
        int quantity = 5;

        when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> cartService.updateQuantity(cartId, itemId, quantity))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("장바구니를 찾을 수 없습니다.");
    }

    @Test
    void updateQuantity_장바구니_항목_없음_예외() {
        // given
        Long cartId = 1L;
        Long itemId = 1L;
        int quantity = 5;

        Cart cart = Cart.builder()
                .id(cartId)
                .items(new ArrayList<>()) // 빈 아이템 리스트
                .build();

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        // when & then
        assertThatThrownBy(() -> cartService.updateQuantity(cartId, itemId, quantity))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("장바구니 아이템을 찾을 수 없습니다.");
    }

    @Test
    void removeItemFromCart_장바구니_아이템_제거_성공() {
        // given
        Long cartId = 1L;
        Long itemId = 1L;

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        // when
        cartService.removeItemFromCart(cartId, itemId);

        // then
        assertThat(cart.getItems()).isEmpty();
        verify(cartRepository).findById(cartId);
        verify(cartRepository).save(cart);
    }

    @Test
    void removeItemFromCart_장바구니가_없을_경우_예외발생() {
        // given
        Long cartId = 99L;
        Long itemId = 1L;

        when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> cartService.removeItemFromCart(cartId, itemId))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("장바구니를 찾을 수 없습니다.");

        verify(cartRepository).findById(cartId);
        verify(cartRepository, never()).save(any());
    }

    @Test
    void removeItemFromCart_장바구니_아이템이_없을_경우_예외발생() {
        // given
        Long cartId = 1L;
        Long itemId = 99L; // 존재하지 않는 itemId

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        // when & then
        assertThatThrownBy(() -> cartService.removeItemFromCart(cartId, itemId))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("장바구니 아이템을 찾을 수 없습니다.");

        verify(cartRepository).findById(cartId);
        verify(cartRepository, never()).save(any());
    }

    @Test
    void clearCart_장바구니_초기화_성공() {
        // given
        Long cartId = 1L;
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        // when
        cartService.clearCart(cartId);

        // then
        assertThat(cart.getItems()).isEmpty(); // 장바구니가 비워졌는지 확인
        verify(cartRepository).findById(cartId); // findById 호출 확인
        verify(cartRepository).save(cart); // save 호출 확인
    }

    @Test
    void clearCart_존재하지_않는_장바구니_초기화_시도하면_예외발생() {
        // given
        Long cartId = 1L;
        when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> cartService.clearCart(cartId))
                .isInstanceOf(BusinessException.class)
                .hasMessage("장바구니를 찾을 수 없습니다.");
        verify(cartRepository).findById(cartId); // findById 호출 확인
        verify(cartRepository, never()).save(any()); // save는 호출되지 않아야 함
    }

    @Test
    void 장바구니_아이템_주문_변환_성공() {
        // given
        Long cartId = 1L;
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart)); // 장바구니 반환 설정

        // createOrder 메서드를 모킹하여 원하는 값으로 반환 -> 실제 객체의 메서드 모킹할 수 없음
//        when(cartService.createOrder(cart)).thenReturn(order); // 원하는 주문 객체 반환

        when(orderRepository.save(any(Order.class))).thenReturn(order); // 주문 저장 설정

        // when
        OrderResponseDto response = cartService.convertCartToOrder(cartId); // 서비스 메서드 호출

        // then
        assertThat(response).isNotNull();
        assertThat(response.getUserId()).isEqualTo(userEntity.getId()); // 사용자 ID 확인
        assertThat(response.getOrderDate()).isNotNull(); // 주문 날짜 확인
        assertThat(response.getTotalPrice()).isEqualTo(200); // 가격 검증 (100 * 2)
        assertThat(response.getItems()).hasSize(1); // 아이템이 하나인 경우
        assertThat(response.getItems().get(0).getProductId()).isEqualTo(product.getId()); // 상품 ID 확인
        assertThat(response.getItems().get(0).getTitle()).isEqualTo("Test Book"); // 상품 제목 확인
        assertThat(response.getItems().get(0).getQuantity()).isEqualTo(2); // 수량 확인
        assertThat(response.getItems().get(0).getTotalPrice()).isEqualTo(200); // 총 가격 검증 (100 * 2)
    }
}
