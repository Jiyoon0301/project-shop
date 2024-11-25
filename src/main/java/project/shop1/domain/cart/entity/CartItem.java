package project.shop1.domain.cart.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import project.shop1.domain.product.entity.Book;
import project.shop1.domain.user.entity.UserEntity;

@Entity
@Data
@Table(name = "cart")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItem {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_entity_id")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // 해도되냐?
    private Book book;

    private int quantity;
}
