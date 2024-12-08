package project.shop1.domain.cart.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import project.shop1.domain.cart.entity.Cart;
import project.shop1.domain.cart.entity.CartItem;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CartResponseDto {
    private Long id;
    private Long userId;
    private List<CartItemResponseDto> items;

    public CartResponseDto(Cart cart) {
        this.id = cart.getId();
        this.userId = cart.getUserEntity().getId();
        this.items = new ArrayList<>();

        for (CartItem item : cart.getItems()) {
            CartItemResponseDto responseDto = new CartItemResponseDto();
            responseDto.setItemId(item.getId());
            responseDto.setProductId(item.getBook().getId());
            responseDto.setTitle(item.getBook().getTitle());
            responseDto.setQuantity(item.getQuantity());
            responseDto.setPrice(item.getPrice());
            responseDto.setTotalPrice(item.getPrice() * item.getQuantity());

            this.items.add(responseDto);
        }
    }
}
