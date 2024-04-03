package project.shop1.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="book_id")
    private Book book;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; //주문 가격
    private int count; //주문 수량

    //==생성 메서드==//
    public static OrderItem createOrderItem(Book book, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setBook(book);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        book.removeStock(count); //주문한 만큼 재고 감소 시키기
        return orderItem;
    }

    public int getTotalPrice() {
        return getOrderPrice()*getCount();
    }
}
