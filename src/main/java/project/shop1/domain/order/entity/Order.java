package project.shop1.domain.order.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import project.shop1.domain.user.entity.UserEntity;
import project.shop1.domain.order.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_entity_id", nullable = false)
    private UserEntity userEntity; // 주문자

    private String address; // 주문한 사람이 배송받을 주소

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus; // 주문처리상태 [PENDING, ORDER, CANCEL]

    private LocalDateTime orderDate; // 주문일자

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true) //Order persist 될 때마다 OrderItem도 persist됨
    private List<OrderItem> orderItems = new ArrayList<>(); //주문상품

    // TODO: Cannot drop table 'delivery' referenced by a foreign key constraint 'FK7k5g81aklxrspa4fenlg60ti3' on table 'orders'. 에러 해결
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true) //Order persist 될 때마다 Delivery도 persist됨
    @JoinColumn(name = "delivery_id", foreignKey = @ForeignKey(name = "FK_delivery_orders"))
    private Delivery delivery;

    private int totalPrice;

    //==연관관계 메서드==//
    public void setUserEntity(UserEntity userEntity) { //userEntity와 Order를 묶어주는 연관관계 메서드
        this.userEntity = userEntity;
        userEntity.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    //==생성 메서드==// Order는 연관관계가 복잡하기 때문에 생성메서드를 만들어 두면 편리
    public static Order createOrder(UserEntity userEntity, String address, Delivery delivery, List<OrderItem> orderItems) {
        Order order = new Order();
        order.setUserEntity(userEntity);
        order.setAddress(address);
        order.setOrderStatus(OrderStatus.ORDER); //ORDER 상태로 초기화 해놓기
        order.setOrderDate(LocalDateTime.now()); //현재 시간
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
//        order.setDelivery(delivery);
        return order;
    }

    //==조회 로직==//

    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getOrderPrice();
        }
        return totalPrice;
    }
}
