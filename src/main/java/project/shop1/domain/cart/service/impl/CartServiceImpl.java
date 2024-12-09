package project.shop1.domain.cart.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.domain.cart.dto.request.AddProductRequestDto;
import project.shop1.domain.cart.dto.request.CartItemRequestDto;
import project.shop1.domain.cart.dto.response.CartItemResponseDto;
import project.shop1.domain.cart.dto.response.CartResponseDto;
import project.shop1.domain.cart.entity.Cart;
import project.shop1.domain.cart.entity.CartItem;
import project.shop1.domain.cart.repository.CartItemRepository;
import project.shop1.domain.product.entity.Book;
import project.shop1.domain.product.repository.ProductRepository;
import project.shop1.domain.user.entity.UserEntity;
import project.shop1.domain.user.repository.UserRepository;
import project.shop1.global.exception.BusinessException;
import project.shop1.global.exception.ErrorCode;
import project.shop1.domain.cart.repository.CartRepository;
import project.shop1.domain.cart.service.CartService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final ModelMapper modelMapper;

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
        // 장바구니 가져오기
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "장바구니를 찾을 수 없습니다."));

        // 장바구니 항목 가져오기 및 DTO 변환
        List<CartItemResponseDto> responseDtos = new ArrayList<>();
        for (CartItem cartItem : cart.getItems()) {
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
}
