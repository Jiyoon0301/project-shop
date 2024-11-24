package project.shop1.domain.order.repository;

import project.shop1.domain.order.entity.Order;

import java.util.List;

public interface OrderRepository {

    /* 주문 저장 */
    void saveOrder(Order order);

    /* 주소 저장 */
    void saveAddress(String account, String roadAddress, String detailedAddress);

    /* userEntity account로 order 찾기 */
    List<Order> findOrdersByUserEntityAccount(String account);



    }
