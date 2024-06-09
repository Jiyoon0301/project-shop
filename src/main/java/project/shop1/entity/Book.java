package project.shop1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import project.shop1.common.exception.BusinessException;
import project.shop1.common.exception.ErrorCode;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {

    /* 상품 id */
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    /* 상품 이름 */
    private String title;

    /* 상품 번호 */
    private Long productNumber;

    /* 상품 저자 */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    private String authorName;

    /* 상품 가격 */
    private int price;

    /* 상품 재고 */
    private int stockQuantity;

    /* 상품 판매량 */
    private int sold;

    /* 상품 카테고리 */
    private String category;

    /* 상품의 리뷰 */
    @OneToMany(mappedBy = "book")
    private List<Review> reviews = new ArrayList<>();

    /* 평점 평균 - 상품의 전체 평점들을 더한 값의 평균 */
    private double averageRating;


//    /* 이미지 경로 */
//    private String uploadPath;
//
//    /* 이미지 uuid */
//    private String uuid;
//
//    /* 파일 이름 */
//    private String fileName;

    /* 연관관계 메서드 */
     public void addReview(Review review){
         this.reviews.add(review);
     }

    //재고 증가
    public void addStock(int quantity){this.stockQuantity+=quantity;}

    //재고 감소, 0 이하 불가능
    public void removeStock(int quantity)throws BusinessException{
        int restStock=this.stockQuantity-quantity;
        if(restStock<0){
            throw new BusinessException(ErrorCode.INSUFFICIENT_STOCK, "재고가 부족합니다.");
//            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity=restStock;
    }

    /* 평점 평균 메서드 */
    public void calAverageRating(){
        if (this.reviews.size()==0){
            this.averageRating = 0.0;
        }else{
            double totalRating = 0.0;
            for (Review review : this.reviews) {
                totalRating += review.getRating();
            }
            this.averageRating = (totalRating/this.reviews.size());
        }
    }
}
