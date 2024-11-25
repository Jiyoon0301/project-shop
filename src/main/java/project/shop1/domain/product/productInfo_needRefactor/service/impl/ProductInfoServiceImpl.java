package project.shop1.domain.product.productInfo_needRefactor.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.domain.product.productInfo_needRefactor.dto.ProductInfoRequestDto;
import project.shop1.domain.product.productInfo_needRefactor.repository.ProductInfoRepository;
import project.shop1.domain.product.productInfo_needRefactor.service.ProductInfoService;
import project.shop1.global.exception.BusinessException;
import project.shop1.global.exception.ErrorCode;
import project.shop1.domain.product.entity.Book;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductInfoServiceImpl implements ProductInfoService {

    private final ProductInfoRepository productInfoRepository;

    /* 상품 상세 페이지 */
    @Override
    public Optional<Book> productInfo(ProductInfoRequestDto productInfoRequestDto) {
        Long productNubmer = productInfoRequestDto.getProductNumber();
        Optional<Book> result = productInfoRepository.findBookByProductNumber(productNubmer);
        if(result.isEmpty()){
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "등록된 상품이 없습니다.");
        }
        return result;
    }

}
