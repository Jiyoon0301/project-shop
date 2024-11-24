package project.shop1.domain.product_refact.entity;

import jakarta.persistence.*;
import lombok.*;
import project.shop1.domain.product_refact.entity.Book;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor //모든 필드 사용 생성자
@NoArgsConstructor
@Builder
@ToString(exclude = "book")
public class Author {

    @Id
    @GeneratedValue
    private Long id;

    private Long authorNumber;

    // 작가 이름
    private String name;

    // 작가 소속 국가
    private String nation;

    // 작가 소개
    private String authorIntro;

    // 등록 날짜
    private LocalDate regDate;

    // 수정 날짜
    private LocalDate updateDate;

    // 책
    @OneToMany(mappedBy = "author")
    private List<Book> book = new ArrayList<>();

    public Author(String name) {
        this.name = name;
    }
}
