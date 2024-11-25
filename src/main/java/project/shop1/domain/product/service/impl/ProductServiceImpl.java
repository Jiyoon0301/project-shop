package project.shop1.domain.product.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.domain.product.dto.RequestDto.AddProductRequestDto;
import project.shop1.domain.product.service.ProductService;
import project.shop1.global.exception.BusinessException;
import project.shop1.global.exception.ErrorCode;
import project.shop1.domain.product.entity.Author;
import project.shop1.domain.product.entity.Book;
import project.shop1.domain.product.author_needRefactor.dto.RequestDto.FindAuthorByNameRequestDto;
import project.shop1.domain.product.author_needRefactor.service.AuthorService;
import project.shop1.domain.product.dto.ResponseDto.SearchProductsResponseDto;
import project.shop1.domain.product.repository.ProductRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final AuthorService authorService;


    /* 상품 조회 - 제목으로 검색
     */
    @Override
    public SearchProductsResponseDto findProductsByKeyword(String title) {
        Book book = productRepository.findByTitle(title).get();

        return SearchProductsResponseDto.builder()
                .bookTitle(book.getTitle())
                .authorName(book.getAuthor().getName())
                .price(book.getPrice())
                .stockQuantity(book.getStockQuantity())
                .build();
    }

    @Override
    public SearchProductsResponseDto findProductById(Long id) {
        Book book = productRepository.findById(id).get();

        return SearchProductsResponseDto.builder()
                .bookTitle(book.getTitle())
                .authorName(book.getAuthor().getName())
                .price(book.getPrice())
                .stockQuantity(book.getStockQuantity())
                .build();
    }

    /* 상품 등록 */
    @Override
    @Transactional
    public Long addProduct(AddProductRequestDto addProductRequestDto) {
        String title = addProductRequestDto.getTitle();
        String authorName = addProductRequestDto.getAuthorName();
        int price = addProductRequestDto.getPrice();
        int stockQuantity = addProductRequestDto.getStockQuantity();
        String category = addProductRequestDto.getCategory();

        // 이미 등록된 상품인지 확인
        Optional<Book> registedBook = productRepository.findByTitle(title);
        if (registedBook.isPresent()) {
            System.out.println("title:" + registedBook.get().getTitle());
            throw new BusinessException(ErrorCode.RESOURCE_CONFLICT, "이미 사용 중인 상품명입니다.");
        }

        FindAuthorByNameRequestDto dto = new FindAuthorByNameRequestDto(authorName);
        Author author = authorService.findAuthorByName(dto).get();

        Book newBook = Book.builder()
                .title(title)
                .author(author)
                .authorName(authorName) // authorName 제거 필요
                .price(price)
                .stockQuantity(stockQuantity)
                .category(category)
                .build();

        productRepository.saveProduct(newBook);

        return newBook.getId();
    }
}
