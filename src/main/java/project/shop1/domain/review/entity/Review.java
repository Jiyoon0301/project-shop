package project.shop1.domain.review.entity;

import jakarta.persistence.*;
import lombok.*;
import project.shop1.domain.product.entity.Book;
import project.shop1.domain.user.entity.UserEntity;

import java.time.LocalDateTime;

@Entity
@Builder
@Data
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

    // 상품에 대한 리뷰 내용
    private String content;

    // 상품의 평점
    private int rating;

    private LocalDateTime regDate;
}
