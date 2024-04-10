package project.shop1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import project.shop1.common.exception.BusinessException;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonIgnoreProperties("orderItems")
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="book_id")
    private Book book;

//    @JsonIgnoreProperties("orderItems")
    @JsonIgnore
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; //주문 가격
    private int count; //주문 수량

    //==생성 메서드==//
    public static OrderItem createOrderItem(Book book, int orderPrice, int count) throws BusinessException {
        OrderItem orderItem = new OrderItem();
        orderItem.setBook(book);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        book.removeStock(count); //주문한 만큼 재고 감소 시키기
        return orderItem;
    }

    /* 연관관계 메서드 */
    public void setOrder(Order order){ //order, orderItem
        this.order=order;
    }

    public int getTotalPrice() {
        return getOrderPrice()*getCount();
    }
}
