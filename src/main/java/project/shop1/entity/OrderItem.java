package project.shop1.entity;

import jakarta.persistence.*;

public class OrderItem {

    @Id @GeneratedValue
    private Long id;

//    @ManyToOne(fetch= FetchType.LAZY)
//    @JoinColumn(name = "order_id")
//    private Order order;

    public int getTotalPrice() {
        return 0;
    }
}
