package project.shop1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class UserEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String password;
    @NotEmpty
    private String name;
    private String phoneNumber;
    private String email;


    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();


}
