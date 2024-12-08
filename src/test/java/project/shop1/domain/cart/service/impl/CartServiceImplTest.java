package project.shop1.domain.cart.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import project.shop1.domain.cart.dto.request.AddProductRequestDto;
import project.shop1.domain.cart.dto.request.CartItemRequestDto;
import project.shop1.domain.cart.dto.response.CartItemResponseDto;
import project.shop1.domain.cart.dto.response.CartResponseDto;
import project.shop1.domain.cart.entity.Cart;
import project.shop1.domain.cart.entity.CartItem;
import project.shop1.domain.cart.repository.CartRepository;
import project.shop1.domain.cart.service.CartService;
import project.shop1.domain.product.entity.Book;
import project.shop1.domain.product.repository.ProductRepository;
import project.shop1.domain.user.entity.UserEntity;
import project.shop1.global.exception.BusinessException;

import java.util.ArrayList;
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
    private ModelMapper modelMapper;

    // Mock 데이터
    private CartItemRequestDto requestDto;
    private Cart cart;
    private Book product;

    @BeforeEach
    void setUp() {
        requestDto = new CartItemRequestDto();
        requestDto.setProductId(1L); // 상품 ID 설정
        requestDto.setQuantity(2);   // 수량 설정

        product = new Book();
        product.setId(1L);           // 상품 ID 설정
        product.setPrice(100);       // 상품 가격 설정

        cart = new Cart();
        cart.setId(1L);              // 장바구니 ID 설정
    }

    @Test
    void 장바구니에_상품_추가_성공() {
        // given
        when(cartRepository.findById(cart.getId())).thenReturn(Optional.of(cart)); // 장바구니 존재 확인
        when(productRepository.findById(requestDto.getProductId())).thenReturn(Optional.of(product)); // 상품 존재 확인
        when(cartRepository.save(any(Cart.class))).thenReturn(cart); // 장바구니 저장

        CartItem cartItem = new CartItem(cart, product, requestDto.getQuantity(), product.getPrice());
        when(modelMapper.map(any(CartItem.class), eq(CartItemResponseDto.class))).thenReturn(new CartItemResponseDto()); // modelMapper Mocking

        // when
        CartItemResponseDto responseDto = cartService.addItemToCart(cart.getId(), requestDto);

        // then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getProductId()).isEqualTo(product.getId()); // 상품 ID 확인
        assertThat(responseDto.getQuantity()).isEqualTo(requestDto.getQuantity()); // 수량 확인
        assertThat(responseDto.getTotalPrice()).isEqualTo(product.getPrice() * requestDto.getQuantity()); // 총 가격 확인

        // verify
        verify(cartRepository, times(1)).save(any(Cart.class)); // cartRepository.save()가 1번 호출되었는지 확인
    }

    @Test
    void 장바구니에_상품_추가_실패_장바구니_존재하지_않음() {
        // given
        when(cartRepository.findById(cart.getId())).thenReturn(Optional.empty()); // 장바구니가 없을 경우

        // when & then
        assertThatThrownBy(() -> cartService.addItemToCart(cart.getId(), requestDto))
                .isInstanceOf(BusinessException.class) // 예외가 발생하는지 확인
                .hasMessage("장바구니를 찾을 수 없습니다.");
    }

    @Test
    void 장바구니에_상품_추가_실패_상품_존재하지_않음() {
        // given
        when(cartRepository.findById(cart.getId())).thenReturn(Optional.of(cart)); // 장바구니 존재
        when(productRepository.findById(requestDto.getProductId())).thenReturn(Optional.empty()); // 상품이 없을 경우

        // when & then
        assertThatThrownBy(() -> cartService.addItemToCart(cart.getId(), requestDto))
                .isInstanceOf(BusinessException.class) // 예외가 발생하는지 확인
                .hasMessage("상품을 찾을 수 없습니다.");
    }

    @Test
    void 장바구니에_새로운_상품_추가_성공() {
        // given
        Long userId = 1L;
        AddProductRequestDto requestDto = new AddProductRequestDto();
        requestDto.setProductId(1L);
        requestDto.setQuantity(2);

        UserEntity user = new UserEntity();
        user.setId(userId);

        Cart cart = new Cart();
        cart.setId(1L);
        cart.setUserEntity(user);
        cart.setItems(new ArrayList<>());

        Book product = new Book();
        product.setId(1L);
        product.setTitle("Test Book");
        product.setPrice(100);

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

        // Verify interactions
        verify(cartRepository, times(1)).findByUserEntity_Id(userId);
        verify(productRepository, times(1)).findById(requestDto.getProductId());
        verify(cartRepository, times(1)).save(cart);
    }
}
