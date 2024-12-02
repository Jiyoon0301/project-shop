package project.shop1.domain.order.dto;

import lombok.Data;

@Data
public class SearchAddressRequestDto {
    String keyword;
    int pageNumber;
}
