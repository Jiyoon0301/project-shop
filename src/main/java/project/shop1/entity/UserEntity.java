package project.shop1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import project.shop1.enums.Rank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
public class UserEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String password;
    @NotEmpty
    private String name;
    private String phoneNumber;
    private String email;
    private Rank rank;


    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public UserEntity(){ //기본생성자

    }


}
