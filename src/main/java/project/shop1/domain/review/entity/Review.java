package project.shop1.domain.review.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
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

    // 리뷰 내용
    private String content;

    // 평점
    private int rating;

    // 등록일
    @CreationTimestamp
    private LocalDateTime regDate; // @CreationTimestamp로 자동 저장
}
