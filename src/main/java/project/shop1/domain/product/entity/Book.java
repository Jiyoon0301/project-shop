package project.shop1.domain.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import project.shop1.domain.review.entity.Review;
import project.shop1.global.exception.BusinessException;
import project.shop1.global.exception.ErrorCode;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {

    /* 상품 id */
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    /* 상품 이름 */
    private String title;

    /* 상품 가격 */
    private int price;

    /* 상품 재고 */
    private int stockQuantity;

    /* 저자 이름 */
    private String authorName;

    /* 상품 카테고리 */
    private String category;

    /* 상품의 리뷰 */
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

//    /* 이미지 경로 */
//    private String uploadPath;
//
//    /* 이미지 uuid */
//    private String uuid;
//
//    /* 파일 이름 */
//    private String fileName;

    public Book(String title, int price) {
        this.title = title;
        this.price = price;
    }

    public Book(String title, int price, int stockQuantity, String authorName, String category) {
        this.title = title;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.authorName = authorName;
        this.category = category;
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }

    //재고 증가
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    //재고 감소, 0 이하 불가능
    public void removeStock(int quantity) throws BusinessException {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new BusinessException(ErrorCode.INSUFFICIENT_STOCK, "재고가 부족합니다.");
        }
        this.stockQuantity = restStock;
    }
}