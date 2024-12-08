package project.shop1.domain.cart.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import project.shop1.domain.product.entity.Book;
import project.shop1.domain.user.entity.UserEntity;

@Entity
@Data
@Table(name = "cart_items")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    private int quantity;

    private int price;

    public CartItem(Cart cart, Book book, int quantity, int price) {
        this.cart = cart;
        this.book = book;
        this.quantity = quantity;
        this.price = price;
    }

    public int calculateItemTotal() {
        return price * quantity;
    }
}
