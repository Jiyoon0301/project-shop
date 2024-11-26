package project.shop1.domain.product.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "book")
public class Author {

    @Id
    @GeneratedValue
    private Long id;
    // 작가 이름
    private String name;
    // 작가 국적
    private String nation;
    // 작가 소개
//    private String bio;

    // 책
    @OneToMany(mappedBy = "author")
    private List<Book> book = new ArrayList<>();
}
