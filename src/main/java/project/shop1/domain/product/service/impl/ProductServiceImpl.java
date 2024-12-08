package project.shop1.domain.product.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.domain.product.dto.RequestDto.ProductUpdateRequestDto;
import project.shop1.domain.product.dto.ResponseDto.ProductResponseDto;
import project.shop1.domain.product.dto.RequestDto.ProductRequestDto;
import project.shop1.domain.product.entity.Book;
import project.shop1.domain.product.service.ProductService;
import project.shop1.domain.product.repository.ProductRepository;
import project.shop1.global.exception.BusinessException;
import project.shop1.global.exception.ErrorCode;

import java.util.List;
import java.util.stream.Collectors;

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
        if (quantity <= 0) {
            throw new BusinessException(ErrorCode.RESOURCE_CONFLICT, "수량은 0보다 커야합니다.");
        }
        product.addStock(quantity);

        // 저장
        Book updatedProduct = productRepository.save(product);

        // Entity를 DTO로 변환
        return modelMapper.map(updatedProduct, ProductResponseDto.class);
    }

    @Override
    public ProductResponseDto getProductById(Long productId) {
        // productId에 해당하는 상품을 조회
        Book product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "존재하지 않는 상품입니다."));

        // Entity를 DTO로 변환하여 반환
        return modelMapper.map(product, ProductResponseDto.class);
    }

    // 모든 상품 조회
    @Override
    public List<ProductResponseDto> getAllProducts() {
        // 데이터베이스에서 모든 제품 조회
        List<Book> products = productRepository.findAll();

        // Entity -> DTO 변환
        return products.stream()
                .map(product -> modelMapper.map(product, ProductResponseDto.class))
                .collect(Collectors.toList());
    }

    // 키워드로 상품 검색
    @Override
    public List<ProductResponseDto> searchProducts(String keyword) {
        // 데이터베이스에서 제목 또는 기타 필드에 키워드가 포함된 제품 검색
        List<Book> products = productRepository.findByTitleContainingIgnoreCase(keyword);

        // Entity -> DTO 변환
        return products.stream()
                .map(product -> modelMapper.map(product, ProductResponseDto.class))
                .collect(Collectors.toList());
    }

    // 상품 업데이트
    @Override
    @Transactional
    public ProductResponseDto updateProduct(Long productId, ProductUpdateRequestDto updateRequestDto) {
        // Product ID로 데이터베이스에서 제품 조회
        Book existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "존재하지 않는 상품입니다."));

        // Update 요청 내용을 기존 엔티티에 반영
        if (updateRequestDto.getTitle() != null) {
            existingProduct.setTitle(updateRequestDto.getTitle());
        }
        if (updateRequestDto.getPrice() != null) {
            existingProduct.setPrice(updateRequestDto.getPrice());
        }
        if (updateRequestDto.getStockQuantity() != null) {
            existingProduct.setStockQuantity(updateRequestDto.getStockQuantity());
        }
        if (updateRequestDto.getAuthorName() != null) {
            existingProduct.setAuthorName(updateRequestDto.getAuthorName());
        }
        if (updateRequestDto.getCategory() != null) {
            existingProduct.setCategory(updateRequestDto.getCategory());
        }

        // 데이터베이스에 저장 (변경 감지)
        Book updatedProduct = productRepository.save(existingProduct);

        // Entity를 Response DTO로 변환하여 반환
        return modelMapper.map(updatedProduct, ProductResponseDto.class);
    }
}
