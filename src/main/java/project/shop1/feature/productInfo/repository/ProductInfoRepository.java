package project.shop1.feature.productInfo.repository;

import project.shop1.entity.Book;
import project.shop1.entity.CartItem;

import java.util.Optional;

public interface ProductInfoRepository {

    Optional<Book> findBookByProductNumber(Long productNumber);

    /* 장바구니 담기 버튼 */
    void addCart(CartItem cartItem);


}
