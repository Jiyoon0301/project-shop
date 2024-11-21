package project.shop1.domain.product_refact.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import project.shop1.domain.author_refact.service.AuthorService;
import project.shop1.domain.product_refact.dto.RequestDto.AddProductRequestDto;
import project.shop1.domain.product_refact.repository.ProductRepository;

/**
 * 테스트 DB로 H2 쓰기
 */
@ActiveProfiles("test")
// JUnit5에서는 @Mock이 @ExtendWith(MockitoExtension.class)와 함께 사용되어야 테스트가 시작하기 전에 애너테이션을 감지할 수 있다.
@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private AuthorService authorService;

    private AddProductRequestDto addProductRequestDto;

    @BeforeEach
    void setUp() {
        // 상품 등록 RequestDto 설정
        addProductRequestDto = AddProductRequestDto.builder()
                .title("Title")
                .authorName("AuthorName")
                .price(1000)
                .stockQuantity(10)
                .category("fiction")
                .build();
    }

    @Test
    void 상품_등록_정상_동작() {
        // given
//        Author mockAuthor = Mockito.mock(Author.class);
//        Mockito.when(mockAuthor.getName()).thenReturn("AuthorName");
//
//        Mockito.when(authorService.findAuthorByName(new FindAuthorByNameRequestDto("AuthorName"))).thenReturn(Optional.ofNullable(mockAuthor));
//
//        ProductService productService1 = new ProductServiceImpl(productRepository, authorService);
//
//        // when
//        Long saveId = productService1.addProduct(addProductRequestDto);
//
//        //then
//        SearchProductsResponseDto find = productService1.findProductById(saveId);
//        Assertions.assertThat("BookTitle").isEqualTo(find.getBookTitle());
    }

//    @Test
//    void 상품_등록_이미_존재하는_상품() {
//        // given
//        Book existingBook = Book.builder().title("Title").build();
//        when(productRepository.findByTitle(addProductRequestDto.getTitle()))
//                .thenReturn(Optional.of(existingBook));
//
//        // when and then
//        Assertions.assertThatThrownBy(() -> productService.addProduct((addProductRequestDto)))
//                .isInstanceOf(BusinessException.class)
//                .hasMessageContaining("이미 사용 중인 상품명입니다.");
//
//        // 저장 메서드가 호출되지 않았는지 검증
//        verify(productRepository, never()).saveProduct(any(Book.class));
//    }
}
