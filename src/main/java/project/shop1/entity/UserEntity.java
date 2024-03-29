package project.shop1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import project.shop1.entity.enums.Rank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_entity_id")
    private Long id;

    private String account;
    private String password;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    @Enumerated(value=EnumType.STRING)
    private Rank rank;


    @OneToMany(mappedBy = "userEntity")
    private List<Order> orders = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "userEntity")
    private List<CartItem> cartItem = new ArrayList<>(); // cartItems로 바꾸기


}
