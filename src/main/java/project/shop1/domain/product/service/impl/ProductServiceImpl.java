package project.shop1.domain.product.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.domain.product.dto.ResponseDto.ProductResponseDto;
import project.shop1.domain.product.dto.RequestDto.ProductRequestDto;
import project.shop1.domain.product.entity.Book;
import project.shop1.domain.product.service.ProductService;
import project.shop1.domain.product.repository.ProductRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    // 상품 생성
    @Override
    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        // DTO를 Entity로 변환
        Book product = modelMapper.map(productRequestDto, Book.class);
//        Book product = Book.builder()
//                .title(productRequestDto.getTitle())
//                .price(productRequestDto.getPrice())
//                .stockQuantity(productRequestDto.getStockQuantity())
//                .authorName(productRequestDto.getAuthorName())
//                .category(productRequestDto.getCategory())
//                .build();

        // 데이터베이스에 저장
        Book savedProduct = productRepository.save(product);

        // 저장된 Entity를 DTO로 변환
        return modelMapper.map(savedProduct, ProductResponseDto.class);
//        return ProductResponseDto.builder()
//                .id(product.getId())
//                .title(product.getTitle())
//                .price(product.getPrice())
//                .stockQuantity(product.getStockQuantity())
//                .authorName(product.getAuthorName())
//                .category(product.getCategory())
//                .build();
    }
}
