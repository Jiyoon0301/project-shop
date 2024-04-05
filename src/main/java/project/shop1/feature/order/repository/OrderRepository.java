package project.shop1.feature.order.repository;

import project.shop1.entity.Book;
import project.shop1.entity.Order;

import java.util.Optional;

public interface OrderRepository {

    /* 주문 저장 */
    void saveOrder(Order order);

    /* 주소 저장 */
    void saveAddress(String account, String roadAddress, String detailedAddress);

    /* bookId로 상품 찾기 */
    Optional<Book> findBookbyBookId(Long bookId);



    }
