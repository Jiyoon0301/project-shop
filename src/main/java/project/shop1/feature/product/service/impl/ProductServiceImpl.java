package project.shop1.feature.product.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.entity.Author;
import project.shop1.feature.join.dto.JoinRequestDto;
import project.shop1.feature.product.dto.AuthorRequestDto;
import project.shop1.feature.product.dto.ProductRequestDto;
import project.shop1.feature.product.repository.impl.ProductRepositoryImpl;
import project.shop1.feature.product.service.ProductService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService { //핵심 비즈니스 로직 구현

    private final ProductRepositoryImpl productRepositoryImpl;

    /* 상품 등록 메서드 */
    public void productRegistration(ProductRequestDto productRequestDto) {

    }

    /* 상품 관리 메서드 */
    public void productManagement(JoinRequestDto joinRequestDto) {

    }

    /* 작가 등록 메서드 */
    public void authorRegistration(AuthorRequestDto authorRequestDto) {
        String authorName = authorRequestDto.getAuthorName();
        String nation = authorRequestDto.getNation();
        String authorIntro = authorRequestDto.getAuthorIntro();
        LocalDateTime regDate = LocalDateTime.now();
        System.out.println("Service 들어옴");
        System.out.println("현재날짜:");
        System.out.println(regDate);

        Author author = Author.builder()
                .authorName(authorName)
                .nation(nation)
                .authorIntro(authorIntro)
                .regDate(regDate)
                .build();
        productRepositoryImpl.saveAuthor(author);
    }

    /* 작가 관리 메서드 */
    public void authorManagement(JoinRequestDto joinRequestDto) {

    }

}
