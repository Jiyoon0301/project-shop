package project.shop1.feature.product_refact.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.shop1.common.reponse.BooleanResponse;
import project.shop1.common.validation.ValidationSequence;
import project.shop1.entity.Book;
import project.shop1.feature.product_refact.dto.RequestDto.AddProductRequestDto;
import project.shop1.feature.product_refact.dto.ResponseDto.SearchProductsResponseDto;
import project.shop1.feature.product_refact.service.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /* 상품 검색: 상품명(제목)으로 검색
    * searchBooksRequestDto :
    * String criteria, String keyword, int amount, int pageNumber
    * SearchBooksResponseDto:
    * List<Book>
    */
    @GetMapping("/products/serach")
    public SearchProductsResponseDto searchProducts(@Validated(value = ValidationSequence.class) @RequestParam String keyword){
        SearchProductsResponseDto result = productService.findProductsByKeyword(keyword);
        return result;
    }

    /* 상품 목록 조회 */
    @GetMapping("/products")
    public List<Book> getAllProducts(){
        return null;
    }

    /* 상품 상세 조회 */
    @GetMapping("/products/{productNumber}")
    public Book getProductByProductNumber(){
        return null;
    }

    /* 상품 등록
     * AddProductRequestDto:
     * String title, String authorName, int price, int stockQuantity, String category
     */
    @PostMapping("/products")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BooleanResponse> addProduct(@Validated(value = ValidationSequence.class) @RequestBody AddProductRequestDto addProductRequestDto){
        productService.addProduct(addProductRequestDto);

        return ResponseEntity.ok(BooleanResponse.of(true));
    }

    /* 상품 수정
     * UpdateProductInfoRequestDto :
     * Long productNumber, String title, String authorName, int price, int stockQuantity, String category
     */
//    @PutMapping("/products/{productNumber}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<BooleanResponse> updateProduct(@Validated(value = ValidationSequence.class) @RequestBody UpdateProductInfoRequestDto updateProductInfoRequestDto){
////        productService.updateProduct(updateProductInfoRequestDto);
//        return ResponseEntity.ok(BooleanResponse.of(true));
//    }

    /* 상품 삭제
     * DeleteProductRequestDto :
     * Long productNumber
     */
//    @DeleteMapping("/products/{productId}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<BooleanResponse> deleteProduct(@Validated(value = ValidationSequence.class) @RequestBody DeleteProductByProductNumberRequestDto deleteProductByProductNumberRequestDto){
////        productService.deleteProductByProductNumber(deleteProductByProductNumberRequestDto);
//        return ResponseEntity.ok(BooleanResponse.of(true));
//    }
}
