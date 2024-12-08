package project.shop1.domain.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.shop1.domain.cart.entity.Cart;
import project.shop1.domain.product.entity.Book;
import project.shop1.domain.cart.entity.CartItem;
import project.shop1.domain.user.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findById(Long cartId);
}
