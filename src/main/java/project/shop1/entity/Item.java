package project.shop1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.websocket.server.ServerEndpoint;
import lombok.Getter;
import lombok.Setter;
import project.shop1.Exception.NotEnoughStockException;

@Entity
@Getter @Setter
public class Item {

    @Id @GeneratedValue
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;


    //재고 증가
    public void addStock(int quantity){this.stockQuantity+=quantity;}

    //재고 감소, 0 이하 불가능
    public void removeStock(int quantity){
        int restStock=this.stockQuantity-quantity;
        if(restStock<0){
//            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity=restStock;
    }
}
