package project.shop1.feature.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.common.reponse.BooleanResponse;
import project.shop1.common.validation.ValidationSequence;
import project.shop1.entity.Book;
import project.shop1.entity.Author;
import project.shop1.feature.admin.dto.authorDto.AuthorRegistrationRequestDto;
import project.shop1.feature.admin.dto.productDto.*;
import project.shop1.feature.admin.service.AuthorService;
import project.shop1.feature.admin.service.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController { //웹 MVC의 컨트롤러 역할

    private final ProductService productService;
    private final AuthorService authorService;

    /* 상품 등록
    * ProductRegistrationRequestDto :
    * String title, String authorName, int price, int stockQuantity, String category
    */
    @PostMapping("/admin/product-registration")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BooleanResponse> productRegistration(@Validated(value = ValidationSequence.class) @RequestBody ProductRegistrationRequestDto productRegistrationRequestDto){
        productService.productRegistration(productRegistrationRequestDto);

        return ResponseEntity.ok(BooleanResponse.of(true));
    }

    /* 상품 등록 - 작가 검색
    * SearchAuthorRequestDto :
    * String authorName
    */
    @PostMapping("/admin/search-author")
    @PreAuthorize("hasRole('ADMIN')")
    public Author searchAuthor(@Validated(value = ValidationSequence.class) @RequestBody SearchAuthorRequestDto searchAuthorRequestDto){
        return productService.searchAuthor(searchAuthorRequestDto);
    }

    /* 상품 등록 - 새로운 작가 등록
    * AuthorRequestDto :
    * String authorName, String nation, String authorIntro
    */
    @PostMapping("/admin/register-new-author")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BooleanResponse> registerNewAuthor(@Validated(value = ValidationSequence.class) @RequestBody AuthorRegistrationRequestDto authorRegistrationRequestDto){
        authorService.authorRegistration(authorRegistrationRequestDto);

        return ResponseEntity.ok(BooleanResponse.of(true));
    }

    /* 상품 관리 페이지 - 전체 조회
    * AdminGetAllProductRequestDto :
    * int pageNumber
    */
    @PostMapping("/admin/search-all-product")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Book> adminSearchAllProduct(@RequestBody AdminSearchAllProductRequestDto adminSearchAllProductRequestDto){
        List<Book> allBook = productService.adminSearchAllProduct(adminSearchAllProductRequestDto);

        return allBook;
    }

    /* 상품 정보 수정
    * UpdateProductInfoRequestDto :
    * Long productNumber, String title, String authorName, int price, int stockQuantity, String category
    */
    @PostMapping("/update-product-info")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BooleanResponse> updateProductInfo(@Validated(value = ValidationSequence.class) @RequestBody UpdateProductInfoRequestDto updateProductInfoRequestDto){
        productService.updateProductInfo(updateProductInfoRequestDto);
        return ResponseEntity.ok(BooleanResponse.of(true));
    }

    /* 상품 삭제
    * DeleteProductRequestDto :
    * Long productNumber
    */
    @PostMapping("/delete-product-by-product-number")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BooleanResponse> deleteProduct(@Validated(value = ValidationSequence.class) @RequestBody DeleteProductByProductNumberRequestDto deleteProductByProductNumberRequestDto){
        productService.deleteProductByProductNumber(deleteProductByProductNumberRequestDto);
        return ResponseEntity.ok(BooleanResponse.of(true));
    }





}
