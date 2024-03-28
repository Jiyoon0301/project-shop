package project.shop1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

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


//    /* 이미지 경로 */
//    private String uploadPath;
//
//    /* 이미지 uuid */
//    private String uuid;
//
//    /* 파일 이름 */
//    private String fileName;


    //재고 증가
//    public void addStock(int quantity){this.stockQuantity+=quantity;}

    //재고 감소, 0 이하 불가능
    public void removeStock(int quantity){
        int restStock=this.stockQuantity-quantity;
        if(restStock<0){
//            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity=restStock;
    }
}
