package project.shop1.feature.order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class SubmitOrderRequestDto {

    /* 단일 아이템 구매인지 장바구니 구매인지 확인 */
    private Boolean isFromCartPage;

    /* 배송지 */
    @NotBlank(message = "주소를 입력해주세요.", groups=NotBlank.class)
    private String address;

    /* isFromCartPage == true */
    private List<Long> cartItemId;

    /* isFromCartPage == false */
    private Long bookId;
    private int count;


}
