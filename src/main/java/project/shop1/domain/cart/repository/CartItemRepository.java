package project.shop1.domain.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.shop1.domain.cart.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
