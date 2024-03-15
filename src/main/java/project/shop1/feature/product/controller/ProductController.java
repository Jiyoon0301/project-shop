package project.shop1.feature.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.common.reponse.BooleanResponse;
import project.shop1.common.validation.ValidationSequence;
import project.shop1.feature.join.dto.JoinRequestDto;
import project.shop1.feature.product.dto.AuthorRequestDto;
import project.shop1.feature.product.dto.ProductRequestDto;
import project.shop1.feature.product.service.ProductService;
import project.shop1.feature.product.service.impl.ProductServiceImpl;

@RestController
@RequiredArgsConstructor
public class ProductController { //웹 MVC의 컨트롤러 역할

    private final ProductService productService;

    /* 상품 등록 */
    @GetMapping("/productRegistration")
    public ResponseEntity<BooleanResponse> productRegistration(@Validated(value = ValidationSequence.class) @RequestBody ProductRequestDto productRequestDto) throws Exception{

//        productService.productRegistration(productRequestDto);

        return ResponseEntity.ok(BooleanResponse.of(true));
    }

    /* 상품 관리 */
    @PostMapping("/productManagement")
    public ResponseEntity<BooleanResponse> productManagement(JoinRequestDto joinRequestDto) throws Exception{

        return ResponseEntity.ok(BooleanResponse.of(true));
    }

    /* 작가 등록 */
    //인자 productRequestDto : authorName, nation, authorIntro
    @PostMapping("/authorRegistration")
    public ResponseEntity<BooleanResponse> authorRegistraion(@Validated(value = ValidationSequence.class) @RequestBody AuthorRequestDto authorRequestDto) throws Exception{
        productService.authorRegistration(authorRequestDto);

        return ResponseEntity.ok(BooleanResponse.of(true));
    }

    /* 작가 관리 */
    @PostMapping("/authorManagement")
    public ResponseEntity<BooleanResponse> authorManagement(JoinRequestDto joinRequestDto) throws Exception{

        return ResponseEntity.ok(BooleanResponse.of(true));
    }

}
