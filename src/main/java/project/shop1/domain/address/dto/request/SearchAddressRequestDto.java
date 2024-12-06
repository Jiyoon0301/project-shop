package project.shop1.domain.address.dto.request;

import lombok.Data;

@Data
public class SearchAddressRequestDto {
    String keyword;
    int pageNumber;
}
