package project.shop1.domain.product.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import project.shop1.domain.product.dto.RequestDto.AddProductRequestDto;
import project.shop1.domain.product.dto.RequestDto.ProductRequestDto;
import project.shop1.domain.product.dto.RequestDto.ProductUpdateRequestDto;
import project.shop1.domain.product.dto.ResponseDto.ProductResponseDto;
import project.shop1.domain.product.entity.Book;
import project.shop1.domain.product.repository.ProductRepository;
import project.shop1.global.exception.BusinessException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
// JUnit5에서는 @Mock이 @ExtendWith(MockitoExtension.class)와 함께 사용되어야 테스트가 시작하기 전에 애너테이션을 감지할 수 있다.
@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;
    @Mock
    private ProductRepository productRepository;

    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        ReflectionTestUtils.setField(productService, "modelMapper", modelMapper);
    }

    @Test
    void 상품_등록_성공() {
        // Given
        ProductRequestDto requestDto = createProductRequestDto();
        Book productEntity = createBookEntity();
        ProductResponseDto responseDto = createProductResponseDto();

        // Mocking
        when(productRepository.save(any(Book.class))).thenReturn(productEntity);

        // When
        ProductResponseDto result = productService.createProduct(requestDto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo(requestDto.getTitle());
        assertThat(result.getPrice()).isEqualTo(requestDto.getPrice());
        assertThat(result.getStockQuantity()).isEqualTo(requestDto.getStockQuantity());
        assertThat(result.getAuthorName()).isEqualTo(requestDto.getAuthorName());
        assertThat(result.getCategory()).isEqualTo(requestDto.getCategory());

        // Verify
        verify(productRepository, times(1)).save(any(Book.class));
    }

    @Test
    void 상품_재고_추가_성공() {
        // Given
        Long productId = 1L;
        int addQuantity = 5;

        Book existingProduct = createBookEntity();

        // Mocking
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Book.class))).thenReturn(existingProduct);

        // When
        ProductResponseDto updatedProduct = productService.addProduct(productId, addQuantity);

        // Then
        assertThat(updatedProduct).isNotNull();
        assertThat(updatedProduct.getId()).isEqualTo(productId);
        assertThat(updatedProduct.getStockQuantity()).isEqualTo(55); // 기존 10 + 추가 5

        verify(productRepository).findById(productId);
        verify(productRepository).save(existingProduct);
    }

    @Test
    void id가_유효하지_않은_상품의_재고를_추가하려고_하면_예외_발생() {
        // Given
        Long invalidProductId = -1L;
        int addQuantity = 5;

        // Mocking
        when(productRepository.findById(invalidProductId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> productService.addProduct(invalidProductId, addQuantity))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("존재하지 않는 상품입니다.");

        verify(productRepository).findById(invalidProductId);
        verify(productRepository, never()).save(any(Book.class));
    }

    @Test
    void 잘못된_수량을_재고_추가하면_예외_발생() {
        // Given
        Long productId = 1L;
        int invalidQuantity = -5;

        Book existingProduct = createBookEntity();

        // Mocking
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        // When & Then
        assertThatThrownBy(() -> productService.addProduct(productId, invalidQuantity))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("수량은 0보다 커야합니다");

        verify(productRepository).findById(productId);
        verify(productRepository, never()).save(any(Book.class));
    }

    @Test
    void id로_상품_조회_성공() {
        // Given
        Long productId = 1L;
        Book mockBook = createBookEntity();

        // Stub the repository
        when(productRepository.findById(productId)).thenReturn(Optional.of(mockBook));

        // When
        ProductResponseDto result = productService.getProductById(productId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(mockBook.getId());
        assertThat(result.getTitle()).isEqualTo(mockBook.getTitle());
        assertThat(result.getPrice()).isEqualTo(mockBook.getPrice());
        assertThat(result.getStockQuantity()).isEqualTo(mockBook.getStockQuantity());
        assertThat(result.getAuthorName()).isEqualTo(mockBook.getAuthorName());
        assertThat(result.getCategory()).isEqualTo(mockBook.getCategory());

        // Verify repository interaction
        verify(productRepository).findById(productId);
    }

    @Test
    void id가_유효하지_않은_상품을_조회하려고_하면_예외_발생() {
        // Given
        Long productId = 1L;

        // Stub the repository
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> productService.getProductById(productId))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("존재하지 않는 상품입니다.");

        // Verify repository interaction
        verify(productRepository).findById(productId);
    }

    @Test
    void 모든_상품_조회_성공() {
        // Given
        List<Book> mockProducts = List.of(
                Book.builder().id(1L).title("Book 1").price(1000).stockQuantity(10).build(),
                Book.builder().id(2L).title("Book 2").price(2000).stockQuantity(5).build()
        );

        when(productRepository.findAll()).thenReturn(mockProducts);

        // When
        List<ProductResponseDto> result = productService.getAllProducts();

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(0).getTitle()).isEqualTo("Book 1");
        assertThat(result.get(0).getPrice()).isEqualTo(1000);
        assertThat(result.get(1).getId()).isEqualTo(2L);
        assertThat(result.get(1).getTitle()).isEqualTo("Book 2");
        assertThat(result.get(1).getPrice()).isEqualTo(2000);

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void 키워드로_상품_검색_성공() {
        // Given
        String keyword = "Book";
        List<Book> mockBooks = List.of(
                Book.builder().id(1L).title("Book1").price(1000).build(),
                Book.builder().id(2L).title("Book2").price(1500).build()
        );

        // Repository Mock 설정
        when(productRepository.findByTitleContainingIgnoreCase(keyword)).thenReturn(mockBooks);

        // When
        List<ProductResponseDto> result = productService.searchProducts(keyword);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getTitle()).isEqualTo("Book1");
        assertThat(result.get(1).getTitle()).isEqualTo("Book2");

        // Verify Repository 호출 확인
        verify(productRepository, times(1)).findByTitleContainingIgnoreCase(keyword);
    }

    @Test
    void 상품_업데이트_성공() {
        // given
        Long productId = 1L;
        ProductUpdateRequestDto updateRequestDto = new ProductUpdateRequestDto(
                "Updated Title", 200, 50, "Updated Author", "Updated Category");

        Book existingProduct = new Book("Original Title", 100, 20, "Original Author", "Original Category");
        Book updatedProduct = new Book("Updated Title", 200, 50, "Updated Author", "Updated Category");

        ProductResponseDto updatedProductResponseDto = new ProductResponseDto(
                1L, "Updated Title", 200, 50, "Updated Author", "Updated Category");

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(updatedProduct);

        ProductResponseDto responseDto = modelMapper.map(updatedProduct, ProductResponseDto.class);

        // when
        ProductResponseDto result = productService.updateProduct(productId, updateRequestDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Updated Title");
        assertThat(result.getPrice()).isEqualTo(200);
        assertThat(result.getStockQuantity()).isEqualTo(50);
        assertThat(result.getAuthorName()).isEqualTo("Updated Author");
        assertThat(result.getCategory()).isEqualTo("Updated Category");

        // Verify Repository 호출 확인
        verify(productRepository).findById(productId);
        verify(productRepository).save(existingProduct);
    }

    @Test
    void 상품_삭제_성공() {
        // given
        Long productId = 1L;
        Book existingProduct = createBookEntity();

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        // when
        productService.deleteProduct(productId);

        // then
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).delete(existingProduct);
    }

    @Test
    void 존재하지_않는_상품_삭제_시도시_예외_발생() {
        // given
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> productService.deleteProduct(productId))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("존재하지 않는 상품입니다.");

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, never()).delete(any(Book.class));
    }

    @Test
    void 재고_수량_업데이트_성공() {
        // given
        Long productId = 1L;
        int quantityToAdd = 5;

        Book existingProduct = createBookEntity();

        Book updatedProduct = Book.builder()
                .id(productId)
                .title("Test Book")
                .price(1000)
                .stockQuantity(55) // 50 + 5
                .build();

        ProductResponseDto expectedResponse = ProductResponseDto.builder()
                .id(productId)
                .title("Test Book")
                .price(1000)
                .stockQuantity(55)
                .build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(updatedProduct);

        // when
        ProductResponseDto result = productService.updateStock(productId, quantityToAdd);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(productId);
        assertThat(result.getStockQuantity()).isEqualTo(55);
        assertThat(result.getTitle()).isEqualTo("Test Book");

        verify(productRepository).findById(productId);
        verify(productRepository).save(existingProduct);
    }

    @Test
    void 재고가_음수이면_예외_발생() {
        // given
        Long productId = 1L;
        int quantityToSubtract = -60;

        Book existingProduct = createBookEntity();

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        // when & then
        assertThatThrownBy(() -> productService.updateStock(productId, quantityToSubtract))
                .isInstanceOf(BusinessException.class)
                .hasMessage("재고 수량은 음수가 될 수 없습니다.");

        verify(productRepository).findById(productId);
        verify(productRepository, never()).save(any());
    }

    @Test
    void 존재하지_않는_상품의_재고_수량을_업데이트_시도하면_예외_발생() {
        // Given
        Long productId = 999L;
        int quantity = 10;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> productService.updateStock(productId, quantity))
                .isInstanceOf(BusinessException.class)
                .hasMessage("존재하지 않는 상품입니다.");

        verify(productRepository).findById(productId);
        verify(productRepository, never()).save(any());
    }

    @Test
    void 재고_조회_성공() {
        // given
        Long productId = 1L;
        Book existingProduct = createBookEntity();

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        // when
        int stockLevel = productService.getStockLevel(productId);

        // then
        assertThat(stockLevel).isEqualTo(50);

        verify(productRepository).findById(productId);
    }

    private ProductRequestDto createProductRequestDto() {
        return ProductRequestDto.builder()
                .title("Test Book")
                .price(1000)
                .stockQuantity(50)
                .authorName("Test Author")
                .category("Fiction")
                .build();
    }

    private Book createBookEntity() {
        return Book.builder()
                .id(1L)
                .title("Test Book")
                .price(1000)
                .stockQuantity(50)
                .authorName("Test Author")
                .category("Fiction")
                .build();
    }

    private ProductResponseDto createProductResponseDto() {
        return ProductResponseDto.builder()
                .id(1L)
                .title("Test Book")
                .price(1000)
                .stockQuantity(50)
                .authorName("Test Author")
                .category("Fiction")
                .build();
    }
}