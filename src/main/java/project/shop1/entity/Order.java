package project.shop1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.shop1.common.enums.OrderStatus;

import java.lang.reflect.Member;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id; //주문번호

    private LocalDateTime orderDate; //주문일자

    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id")
    private UserEntity userEntity; //주문자

    private OrderStatus status; //주문처리상태 [ORDER, CANCEL]

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) //Order가 persist될 때마다 OrderItem도 persist됨
    private List<OrderItem> orderItems = new ArrayList<>(); //주문상품

    private String address; //배송지 정보

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) //Order가 persist될 때마다 Delivery도 persist됨
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

//    public int getTotalPrice(){ //총 결제금액
//        int totalPrice=0;
//        for (OrderItem orderItem : orderItems){
//            totalPrice += orderItem.getTotalPrice();
//        }
//        return totalPrice;
//    }

    //==연관관계 메서드==//
    public void setUserEntity(UserEntity uesrEntity){ //userEntity와 Order를 묶어주는 연관관계 메서드
        this.userEntity=userEntity;
        userEntity.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery=delivery;
        delivery.setOrder(this);
    }

    //==생성 메서드==// Order는 연관관계가 복잡하기 때문에 생성메서드를 만들어 두면 편리하다
    public static Order createOrder(UserEntity userEntity, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setUserEntity(userEntity);
        order.setDelivery(delivery);
        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER); //ORDER 상태로 초기화 해놓기
        order.setOrderDate(LocalDateTime.now()); //현재 시간
        return order;
    }

    //==조회 로직==//
    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice(){
        int totalPrice=0;
        for (OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
