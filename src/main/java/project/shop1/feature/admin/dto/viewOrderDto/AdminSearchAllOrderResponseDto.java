package project.shop1.feature.admin.dto.viewOrderDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import project.shop1.entity.enums.DeliveryStatus;

import java.time.LocalDate;


@Data
@AllArgsConstructor
public class AdminSearchAllOrderResponseDto {

    /* 주문 번호 */
    private Long orderId;

    /* 주문 아이디 */
    private String account;

    /* 주문 날짜 */
    private LocalDate orderDate;

    /* 주문 상태 */
    private DeliveryStatus delivery;

    /* 취소 */
    private Boolean cancelled;

}
