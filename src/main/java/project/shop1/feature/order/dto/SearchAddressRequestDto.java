package project.shop1.feature.order.dto;

import lombok.Data;

@Data
public class SearchAddressRequestDto {

    /* 주소 검색할 키워드 */
    private String keyword;

    /* 검색된 주소지 목록 페이지 수 */
    int pageNumber;
}
