package project.shop1.domain.address.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class SearchAddressResponseDto {
    private String roadAddrPart1;
    private String jibunAddr;
}
