package project.shop1.feature.product_refact.service.ProductServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.common.exception.BusinessException;
import project.shop1.common.exception.ErrorCode;
import project.shop1.entity.Author;
import project.shop1.entity.Book;
import project.shop1.feature.admin.dto.productDto.ProductRegistrationRequestDto;
import project.shop1.feature.product_refact.dto.RequestDto.AddProductRequestDto;
import project.shop1.feature.product_refact.repository.ProductRepository;
import project.shop1.feature.product_refact.service.ProductService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
//    private final AuthorService authorService; // 생성 필요


    /* 상품 등록 */
    @Override
    @Transactional
    public void productRegistration(AddProductRequestDto addProductRequestDto) {
        String title = addProductRequestDto.getTitle();
        String authorName = addProductRequestDto.getAuthorName();
        int price = addProductRequestDto.getPrice();
        int stockQuantity = addProductRequestDto.getStockQuantity();
        String category = addProductRequestDto.getCategory();
//        Long productNumber = productRepository.setproductNumber() + 1;

        // 이미 등록된 상품인지 확인
        Optional<Book> registedBook = productRepository.findBookByTitle(title);
        if (registedBook.isPresent()){
            System.out.println("title:"+registedBook.get().getTitle());
            throw new BusinessException(ErrorCode.RESOURCE_CONFLICT, "이미 사용 중인 상품명입니다.");
        }

        Author author = authorService.findAuthorByName(authorName).get();

        Book newBook = Book.builder()
                .title(title)
                .author(author)
                .authorName(authorName) // authorName 제거 필요
                .price(price)
                .stockQuantity(stockQuantity)
                .category(category)
                .build();

        System.out.println("book.title:" +title);
        productRepository.saveProduct(newBook);
    }

}
