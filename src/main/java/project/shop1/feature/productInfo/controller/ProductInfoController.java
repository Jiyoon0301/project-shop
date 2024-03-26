package project.shop1.feature.productInfo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.common.reponse.BooleanResponse;
import project.shop1.common.validation.ValidationSequence;
import project.shop1.entity.Book;
import project.shop1.feature.productInfo.dto.AddCartRequestDto;
import project.shop1.feature.productInfo.dto.ProductInfoRequestDto;
import project.shop1.feature.productInfo.service.ProductInfoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ProductInfoController {

    private final ProductInfoService productInfoService;

    /* 상품 상세 페이지 */
    @PostMapping("/product-info") //productInfoRequestDto : Long productNumber
    public Book productInfo(@Validated(value = ValidationSequence.class) @RequestBody ProductInfoRequestDto productInfoRequestDto){
        Optional<Book> result = productInfoService.productInfo(productInfoRequestDto);
        return result.get();
    }

    /* 장바구니에 상품 추가 */
    @PostMapping("/cart/add-cart") //CartRequestDto : Long cartId, String account, Long productNumber, int count
    public ResponseEntity<BooleanResponse> addCart(@Validated(value = ValidationSequence.class) @RequestBody AddCartRequestDto addCartRequestDto){
        productInfoService.addCart(addCartRequestDto);
        return ResponseEntity.ok(BooleanResponse.of(true));
    }
}
