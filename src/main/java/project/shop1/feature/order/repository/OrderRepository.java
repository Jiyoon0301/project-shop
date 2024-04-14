package project.shop1.feature.order.repository;

import project.shop1.entity.Book;
import project.shop1.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    /* 주문 저장 */
    void saveOrder(Order order);

    /* 주소 저장 */
    void saveAddress(String account, String roadAddress, String detailedAddress);

    /* userEntity account로 order 찾기 */
    List<Order> findOrdersByUserEntityAccount(String account);



    }
