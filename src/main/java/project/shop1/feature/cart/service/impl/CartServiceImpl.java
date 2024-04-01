package project.shop1.feature.cart.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.common.exception.BusinessException;
import project.shop1.common.exception.ErrorCode;
import project.shop1.entity.Book;
import project.shop1.entity.CartItem;
import project.shop1.entity.UserEntity;
import project.shop1.feature.cart.dto.*;
import project.shop1.feature.cart.repository.CartRepository;
import project.shop1.feature.cart.service.CartService;
import project.shop1.feature.join.repository.JoinRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final JoinRepository joinRepository;

    /* 장바구니 목록 */
    @Override
    public List<CartItem> findAllCartItemsByUser(FindAllCartItemsByUserRequestDto findAllCartItemsByUserRequestDto) {
        String account = findAllCartItemsByUserRequestDto.getAccount();
        Optional<UserEntity> userEntity = joinRepository.findUserEntityByAccount(account);
        List<CartItem> result = cartRepository.findAllCartItemsByUser(userEntity.get());
        return result;
    }

    /* 장바구니에서 체크된 상품 총 가격 */
    @Override
    public int totalPrice(TotalPriceRequestDto totalPriceRequestDto) {
        List<Long> cartItemsId= totalPriceRequestDto.getCartItemsId();
        /* 입력 초과 방지 */
        if (cartItemsId.size()==0){
            return 0;
        } else {
            return cartRepository.totalPrice(cartItemsId);
        }
    }

    /* 장바구니 담기 버튼 */
    @Override
    @Transactional
    public void addCart(AddCartRequestDto addCartRequestDto){
        String account = addCartRequestDto.getAccount();
        Long productNumber = addCartRequestDto.getProductNumber();
        int quantity = addCartRequestDto.getQuantity();

        /* 중복 상품 방지 */
        Optional<CartItem> findCartItem = cartRepository.findCartItemByProductNumberAndUserAccount(account, productNumber);

        if (findCartItem.isPresent()){
            throw new BusinessException(ErrorCode.RESOURCE_CONFLICT,"장바구니에 이미 존재하는 상품입니다.");
        }

        Optional<UserEntity> userEntity = joinRepository.findUserEntityByAccount(account);

        Optional<Book> book = cartRepository.findBookByProductNumber(productNumber);

        CartItem cartItem = CartItem.builder()
                .userEntity(userEntity.get())
                .book(book.get())
                .quantity(quantity)
                .build();
        cartRepository.addCart(cartItem);

    }

    /* 장바구니에서 삭제 */
    @Override
    @Transactional
    public void deleteCart(DeleteCartRequestDto deleteCartRequestDto) {
        Long cartItemId = deleteCartRequestDto.getCartItemId();
        Optional<CartItem> cartItem = cartRepository.findCartItemById(cartItemId);
        if (cartItem.isEmpty()){
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,"상품이 존재하지 않습니다.");
        }
        cartRepository.deleteCart(cartItem.get());
    }

    /* 장바구니 수량 수정 */
    @Override
    @Transactional
    public void updateProductQuantity(UpdateQuantityByOneRequestDto updateQuantityByOneRequestDto) {
        Long cartItemId = updateQuantityByOneRequestDto.getCartItemId();
        String type = updateQuantityByOneRequestDto.getType();

        if (type.equals("down")){
            if (cartRepository.checkQuantity(cartItemId)==1){
                throw new BusinessException(ErrorCode.RESOURCE_CONFLICT,"장바구니에는 1개 이상 담을 수 있습니다.");
            }
            cartRepository.decreaseQuantity(cartItemId);
        }else {
            cartRepository.increaseQuantity(cartItemId);
        }
    }


    /* 장바구니 확인 */
    @Override
    public CartItem checkCart() {
        return null;
    }


}
