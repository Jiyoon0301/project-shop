package project.shop1.feature.cart.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.entity.CartItem;
import project.shop1.feature.cart.dto.DeleteCartRequestDto;
import project.shop1.feature.cart.dto.FindAllCartItemsByUserRequestDto;
import project.shop1.feature.cart.dto.UpdateProductQuantityRequestDto;
import project.shop1.feature.cart.repository.CartRepository;
import project.shop1.feature.cart.service.CartService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    /* 장바구니 목록 */
    @Override
    public List<CartItem> findAllCartItemsByUser(FindAllCartItemsByUserRequestDto findAllCartItemsByUserRequestDto) {
        String account = findAllCartItemsByUserRequestDto.getAccount();
        List<CartItem> result = cartRepository.findAllCartItemsByUser(account);
        return result;
    }

    /* 장바구니에서 삭제 */
    @Override
    public int deleteCart(DeleteCartRequestDto deleteCartRequestDto) {
        return 0;
    }

    /* 장바구니 수량 수정 */
    @Override
    public int updateProductQuantity(UpdateProductQuantityRequestDto updateProductQuantityRequestDto) {
        return 0;
    }


    /* 장바구니 확인 */
    @Override
    public CartItem checkCart() {
        return null;
    }

//    @Override
//    public Optional<Book> productInfo(ProductInfoRequestDto productInfoRequestDto) {
//        Long productNubmer = productInfoRequestDto.getProductNumber();
//        Optional<Book> result = productInfoRepository.findBookByProductNumber(productNubmer);
//        if(result.isEmpty()){
//            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "등록된 상품이 없습니다.");
//        }
//        return result;
//    }
}
