package project.shop1.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import project.shop1.domain.address.entity.Address;
import project.shop1.domain.cart.entity.Cart;
import project.shop1.domain.cart.entity.CartItem;
import project.shop1.domain.order.entity.Order;
import project.shop1.domain.review.entity.Review;
import project.shop1.domain.user.enums.UserRank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_entity") // indexes = @Index(name = "idx_account", columnList = "account"))
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_entity_id")
    private Long id;

    private String account;
    private String password;
    private String name;
    @Embedded
    private Address address;
    private String phoneNumber;
    private String email;
    private String loginType; // internal, google ...
    @Enumerated(value = EnumType.STRING)
    private UserRank userRank;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>(); // cartItems로 바꾸기

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    public UserEntity(String account, String password, String name, String phoneNumber, String email) {
        this.account = account;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public UserEntity(String name, String email, String role) {
        this.name = name;
        this.email = email;
        this.roles.add(role);
    }

    /* 회원이 가지고 있는 권한(authority) 목록을 SimpleGrantedAuthority로 변환하여 반환 */
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    /* 연관관계 메서드 */

    /* role 추가 메서드 */
    public void addRole(String role) {
        this.roles.add(role);
    }
}
