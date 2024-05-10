package project.shop1.feature.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.common.reponse.BooleanResponse;
import project.shop1.common.validation.ValidationSequence;
import project.shop1.entity.Book;
import project.shop1.entity.Author;
import project.shop1.feature.admin.dto.authorDto.AuthorRequestDto;
import project.shop1.feature.admin.dto.productDto.*;
import project.shop1.feature.admin.service.AuthorService;
import project.shop1.feature.join.dto.JoinRequestDto;
import project.shop1.feature.admin.repository.ProductRepository;
import project.shop1.feature.admin.service.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController { //웹 MVC의 컨트롤러 역할

    private final ProductService productService;
    private final ProductRepository productRepository;
    private final AuthorService authorService;

    /* 상품 등록 */
    @PostMapping("/admin/product-registration") // productRequestDto : String title, String authorname, int price, int stockQuantity, String category, int productNumber
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BooleanResponse> productRegistration(@Validated(value = ValidationSequence.class) @RequestBody ProductRequestDto productRequestDto){
        productService.productRegistration(productRequestDto);

        return ResponseEntity.ok(BooleanResponse.of(true));
    }

    /* 상품 등록 - 작가 검색 */
    @PostMapping("/admin/search-author") //saerchAuthorDto : String authorName
    @PreAuthorize("hasRole('ADMIN')")
    public Author searchAuthor(@Validated(value = ValidationSequence.class) @RequestBody SearchAuthorRequestDto searchAuthorRequestDto){
        return productService.searchAuthor(searchAuthorRequestDto);
    }

    /* 상품 등록 - 새로운 작가 등록 */
    @PostMapping("/admin/register-new-author") // authorRequestDto : String authorName, String nation, String authorIntro
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BooleanResponse> registerNewAuthor(@Validated(value = ValidationSequence.class) @RequestBody AuthorRequestDto authorRequestDto){
        authorService.authorRegistration(authorRequestDto);

        return ResponseEntity.ok(BooleanResponse.of(true));
    }

    /* 상품 관리 페이지 - 전체 조회 */
    @PostMapping("/admin/product-management") // productManagement : int pageNumber
    @PreAuthorize("hasRole('ADMIN')")
    public List<Book> productManagement(@RequestBody ProductManagementDto productManagementDto){
        List<Book> allBook = productService.productManagement(productManagementDto);
        return allBook;
    }

    /* 상품 정보 수정 */
    @PostMapping("/update-productInfo") // updateProdudctInfoRequestDto : Long productNumber, String title, String authorName, int price, int stockQuantity, String category
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BooleanResponse> updateProductInfo(@Validated(value = ValidationSequence.class) @RequestBody UpdateProductInfoRequestDto updateProductInfoRequestDto){
        productService.updateProductInfo(updateProductInfoRequestDto);
        return ResponseEntity.ok(BooleanResponse.of(true));
    }

    /* 상품 삭제 */
    @PostMapping("/delete-product") // deleteProductRequestDto : Long productNumber
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BooleanResponse> deleteProduct(@Validated(value = ValidationSequence.class) @RequestBody DeleteProductRequestDto deleteProductRequestDto){
        productService.deleteProduct(deleteProductRequestDto);
        return ResponseEntity.ok(BooleanResponse.of(true));
    }





}
