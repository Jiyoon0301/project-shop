package project.shop1.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "user_entity_id")
    private UserEntity userEntity;

    /* 리뷰 내용 */
    private String content;

    /* 상품의 평점 */
    private double rating;

    private LocalDateTime regDate;
}
