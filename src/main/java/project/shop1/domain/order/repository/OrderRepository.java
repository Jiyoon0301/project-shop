package project.shop1.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.shop1.domain.order.entity.Order;
import project.shop1.domain.user.entity.UserEntity;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // userAccount로 order 조회
    List<Order> findOrdersByUserEntityAccount(String account);

    // userId로 order 조회
    List<Order> findByUserId(Long userId);

    // 주소 저장
    void saveAddress(String account, String roadAddress, String detailedAddress);
}
