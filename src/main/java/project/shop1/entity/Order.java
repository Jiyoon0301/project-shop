package project.shop1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    private Long id; //주문번호

    private LocalDateTime orderDate; //주문일자

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity userEntity; //주문자

    private OrderStatus status; //주문처리상태

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>(); //주문상품

    private String address; //배송지 정보

    public int getTotalPrice(){ //총 결제금액
        int totalPrice=0;
        for (OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }


}
