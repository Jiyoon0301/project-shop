package project.shop1.domain.order.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import project.shop1.global.exception.BusinessException;
import project.shop1.domain.product.entity.Book;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)  // 부모 테이블의 삭제 시 자식 테이블도 삭제
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private int orderPrice; // 주문 가격 = 주문 수량 * 상품 가격
    private int quantity; // 주문 수량

    public static OrderItem createOrderItem(Book book, int orderPrice, int quantity) throws BusinessException {
        OrderItem orderItem = new OrderItem();
        orderItem.setBook(book);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setQuantity(quantity);

        book.removeStock(quantity); //주문한 만큼 재고 감소 시키기
        return orderItem;
    }

    public void setOrder(Order order) { //order, orderItem
        this.order = order;
    }
}
