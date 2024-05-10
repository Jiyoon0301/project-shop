package project.shop1.feature.cart.dto;

import lombok.Data;

import java.util.List;

@Data
public class TotalPriceRequestDto {
    List<Long> cartItemsId;
}
