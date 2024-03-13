//package project.shop1.feature.product.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import project.shop1.entity.Author;
//import project.shop1.feature.join.dto.JoinRequestDto;
//import project.shop1.feature.product.dto.AuthorRequestDto;
//import project.shop1.feature.product.dto.ProductRequestDto;
//import project.shop1.feature.product.repository.ProductRepository;
//
//import java.time.LocalDateTime;
//
//@Service
//@RequiredArgsConstructor
//public class ProductService { //핵심 비즈니스 로직 구현
//
//    private final ProductRepository productRepository;
//
//    /* 상품 등록 메서드 */
//    public void productRegistration(ProductRequestDto productRequestDto) throws Exception {
//
//    }
//
//    /* 상품 관리 메서드 */
//    public void productManagement(JoinRequestDto joinRequestDto) throws Exception {
//
//    }
//
//    /* 작가 등록 메서드 */
//    public void authorRegistration(AuthorRequestDto authorRequestDto) throws Exception {
//        String authorName = authorRequestDto.getAuthorName();
//        String nation = authorRequestDto.getNation();
//        String authorIntro = authorRequestDto.getAuthorIntro();
//        LocalDateTime regDate = LocalDateTime.now();
//        System.out.println("Service 들어옴");
//
//        Author author = Author.builder()
//                .authorName(authorName)
//                .nation(nation)
//                .authorIntro(authorIntro)
//                .regDate(regDate)
//                .build();
//        productRepository.saveAuthor(author);
//    }
//
//    /* 작가 관리 메서드 */
//    public void authorManagement(JoinRequestDto joinRequestDto) throws Exception {
//
//    }
//
//}
