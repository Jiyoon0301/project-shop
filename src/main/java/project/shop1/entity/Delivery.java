package project.shop1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.shop1.common.exception.BusinessException;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

//    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY) // 필요시 활성화
//    private Order order;

//    @Embedded
    private String address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; // [READY, DELIVERING, COMPLETE]

    /* 생성 메서드 */
    public static Delivery createDelivery(String address, DeliveryStatus status) {
        Delivery delivery = new Delivery();
        delivery.setAddress(address);
        delivery.setStatus(status);
        return delivery;
    }
}
