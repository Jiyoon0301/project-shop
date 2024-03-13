//package project.shop1.feature.product.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//import project.shop1.feature.join.dto.JoinRequestDto;
//import project.shop1.feature.product.dto.AuthorRequestDto;
//import project.shop1.feature.product.dto.ProductRequestDto;
//import project.shop1.feature.product.service.ProductService;
//
//@RestController
//@RequiredArgsConstructor
//public class ProductController { //웹 MVC의 컨트롤러 역할
//
//    private final ProductService productService;
//
//    /* 상품 등록 */
//    @GetMapping("/productRegistration")
//    public ResponseEntity<ResponseDto> productRegistration(ProductRequestDto productRequestDto) throws Exception{
//
////        productService.productRegistration(productRequestDto);
//
//        return new ResponseEntity(new ResponseDto("상품이 등록되었습니다."), new HttpHeaders(), HttpStatus.OK);
//    }
//
//    /* 상품 관리 */
//    @PostMapping("/productManagement")
//    public ResponseEntity<ResponseDto> productManagement(JoinRequestDto joinRequestDto) throws Exception{
//        return new ResponseEntity(new ResponseDto("상품 수정 완료"), new HttpHeaders(), HttpStatus.OK);
//    }
//
//    /* 작가 등록 */
//    //인자 productRequestDto : authorName, nation, authorIntro
//    @PostMapping("/authorRegistration")
//    public ResponseEntity<ResponseDto> authorRegistraion(AuthorRequestDto authorRequestDto) throws Exception{
//        System.out.println(authorRequestDto.getAuthorName());
//
//        //productService.authorRegistration(authorRequestDto);
//        return new ResponseEntity(new ResponseDto("작가가 등록되었습니다."), new HttpHeaders(), HttpStatus.OK);
//    }
//
//    /* 작가 관리 */
//    @PostMapping("/authorManagement")
//    public ResponseEntity<ResponseDto> authorManagement(JoinRequestDto joinRequestDto) throws Exception{
//
//        return new ResponseEntity(new ResponseDto("작가 수정 완료"), new HttpHeaders(), HttpStatus.OK);
//    }
//
//}
