package project.shop1.feature.order.dto;

import lombok.Data;

@Data
public class SaveAddressRequestDto {

    /* 검색된 주소 - 뷰 */
    String roadAddress;

    /* 상세 주소 - 사용자 입력 */
    String detailedAddress;
}
