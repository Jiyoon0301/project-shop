package project.shop1.feature.productInfo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.common.exception.BusinessException;
import project.shop1.common.exception.ErrorCode;
import project.shop1.entity.Book;
import project.shop1.feature.productInfo.dto.ProductInfoRequestDto;
import project.shop1.feature.productInfo.repository.ProductInfoRepository;
import project.shop1.feature.productInfo.service.ProductInfoService;
import project.shop1.feature.productSearch.dto.SearchBookByRankingRequestDto;

import java.util.List;
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
