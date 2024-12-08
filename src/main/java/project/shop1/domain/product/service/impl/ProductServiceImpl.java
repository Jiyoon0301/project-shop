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
import project.shop1.global.exception.BusinessException;
import project.shop1.global.exception.ErrorCode;

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

        // 데이터베이스에 저장
        Book savedProduct = productRepository.save(product);

        // 저장된 Entity를 DTO로 변환
        return modelMapper.map(savedProduct, ProductResponseDto.class);
    }

    // 상품 재고 추가
    @Override
    @Transactional
    public ProductResponseDto addProduct(Long productId, int quantity) {
        // 상품 조회
        Book product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "존재하지 않는 상품입니다."));

        // 재고 추가
        product.addStock(quantity);

        // 저장
        Book updatedProduct = productRepository.save(product);

        // Entity를 DTO로 변환
        return modelMapper.map(updatedProduct, ProductResponseDto.class);
    }
}
