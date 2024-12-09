package project.shop1.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.shop1.domain.order.entity.OrderItem;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByBook_Id(Long bookId);
}
