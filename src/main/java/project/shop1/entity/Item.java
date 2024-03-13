package project.shop1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.shop1.entity.Category;
import project.shop1.entity.Delivery;

@Entity
@Getter @Setter
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String title; //제목
    @OneToOne(fetch = FetchType.LAZY)
//    @Column(name = "author_id")
    private Author author; //저자

    private int price; //가격
    private int stockQuantity; //재고
    private int sold; //판매량

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;


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
