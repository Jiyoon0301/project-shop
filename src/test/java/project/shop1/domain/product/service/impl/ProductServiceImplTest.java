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
import project.shop1.domain.product.dto.ResponseDto.ProductResponseDto;
import project.shop1.domain.product.entity.Book;
import project.shop1.domain.product.repository.ProductRepository;

import static org.assertj.core.api.Assertions.assertThat;
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
}
