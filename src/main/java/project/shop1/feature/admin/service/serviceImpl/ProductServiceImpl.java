package project.shop1.feature.admin.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.common.exception.BusinessException;
import project.shop1.common.exception.ErrorCode;
import project.shop1.entity.Author;
import project.shop1.entity.Book;
import project.shop1.feature.admin.dto.productDto.*;
import project.shop1.feature.admin.repository.AuthorRepository;
import project.shop1.feature.admin.repository.ProductRepository;
import project.shop1.feature.admin.service.ProductService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService { //핵심 비즈니스 로직 구현

    private final ProductRepository productRepository;
    private final AuthorRepository authorRepository;

    /* 상품 등록 */
    @Override
    @Transactional
    public void productRegistration(ProductRegistrationRequestDto productRegistrationRequestDto) {
        String title = productRegistrationRequestDto.getTitle();
        String authorName = productRegistrationRequestDto.getAuthorName();
        int price = productRegistrationRequestDto.getPrice();
        int stockQuantity = productRegistrationRequestDto.getStockQuantity();
        String category = productRegistrationRequestDto.getCategory();
        Long productNumber = productRepository.setproductNumber() + 1;

        // 이미 등록된 상품인지 확인
        Optional<Book> registedBook = productRepository.findBookByTitle(title);
        if (registedBook.isPresent()){
            System.out.println("title:"+registedBook.get().getTitle());
            throw new BusinessException(ErrorCode.RESOURCE_CONFLICT, "이미 등록된 상품입니다.");
        }

        Author author = authorRepository.findAuthorByName(authorName).get();

        Book newBook = Book.builder()
                .title(title)
                .productNumber(productNumber)
                .author(author)
                .authorName(authorName)
                .price(price)
                .stockQuantity(stockQuantity)
                .category(category)
                .build();

        System.out.println("book.title:" +title);
        productRepository.saveProduct(newBook);
    }

    /* 상품 등록 - 작가 검색 */
    @Override
    public Author searchAuthor(SearchAuthorRequestDto searchAuthorRequestDto) {
        String authorName = searchAuthorRequestDto.getAuthorName();
        Optional<Author> searchAuthorByName = authorRepository.findAuthorByName(authorName);
        if(searchAuthorByName.isEmpty()){
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "해당하는 이름의 작가가 존재하지 않습니다.");
        }
        return searchAuthorByName.get();

    }


    /* 상품 관리 페이지 - 상품 전체 조회 */
    @Override
    public List<Book> adminSearchAllProduct(AdminSearchAllProductRequestDto adminSearchAllProductRequestDto) {
        int pageNumber = adminSearchAllProductRequestDto.getPageNumber();
        Optional<List<Book>> allBooks = productRepository.findAll(pageNumber);
        System.out.println("book authorName:" + allBooks.get().get(0).getAuthorName());
        if(allBooks.isEmpty()){
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "등록된 상품이 없습니다.");
        }
        return allBooks.get();
    }

    /* 상품 수정 */
    @Override
    @Transactional
    public void updateProductInfo(UpdateProductInfoRequestDto updateProductInfoRequestDto) {
        String authorName = updateProductInfoRequestDto.getAuthorName();
        Optional<Author> author = authorRepository.findAuthorByName(authorName);
        productRepository.updateProductInfo(updateProductInfoRequestDto, author.get());
    }

    /* 상품 삭제 */
    @Override
    @Transactional
    public void deleteProductByProductNumber(DeleteProductByProductNumberRequestDto deleteProductByProductNumberRequestDto) {
        Long productNumber = deleteProductByProductNumberRequestDto.getProductNumber();
        productRepository.deleteProduct(productNumber);
    }

}
