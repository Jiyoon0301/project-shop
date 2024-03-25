package project.shop1.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor //모든 필드 사용 생성자
@NoArgsConstructor
@Builder
public class Author {

    @Id @GeneratedValue
    private Long id;

    private Long authorNumber;

    /* 작가 이름 */
    private String authorName;

    /* 작가 소속 국가 */
    private String nation;

    /* 작가 소개 */
    private String authorIntro;

    /* 등록 날짜 */
    private LocalDate regDate;

    /* 수정 날짜 */
    private LocalDate updateDate;

    /* 책 */
    @OneToMany(mappedBy = "author")
    private List<Book> book = new ArrayList<>();



}
