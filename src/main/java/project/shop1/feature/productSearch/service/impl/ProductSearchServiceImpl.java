package project.shop1.feature.productSearch.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.common.exception.BusinessException;
import project.shop1.common.exception.ErrorCode;
import project.shop1.entity.Book;
import project.shop1.feature.productSearch.dto.SearchBookByRankingRequestDto;
import project.shop1.feature.productSearch.dto.findByKeywordAndCategoryRequestDto;
import project.shop1.feature.productSearch.repository.ProductSearchRepository;
import project.shop1.feature.productSearch.service.ProductSearchService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductSearchServiceImpl implements ProductSearchService {
    private final ProductSearchRepository productSearchRepository;


    /* 상품 검색 - 검색어, 카테고리 */
    @Override
    public List<Book> findByKeywordAndCategory(findByKeywordAndCategoryRequestDto findByKeywordAndCategoryRequestDto) {
        String criteria = findByKeywordAndCategoryRequestDto.getCriteria();
        String keyword = findByKeywordAndCategoryRequestDto.getKeyword();
        String category = findByKeywordAndCategoryRequestDto.getCategory();
        int amount = findByKeywordAndCategoryRequestDto.getAmount();
        int pageNumber = findByKeywordAndCategoryRequestDto.getPageNumber();

        Optional<List<Book>> result = productSearchRepository.findByKeywordAndCategory(criteria,keyword,category,amount,pageNumber);
        if(result.get().isEmpty()){
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "등록된 상품이 없습니다.");
        }
        return result.get();
    }

    /* 인기순위(판매순) 상품 조회 */
    @Override
    public List<Book> searchBookByRanking(SearchBookByRankingRequestDto searchBookByRankingRequestDto) {
        int amount = searchBookByRankingRequestDto.getAmount();
        int pageNumber = searchBookByRankingRequestDto.getPageNumber();
        Optional<List<Book>> result = productSearchRepository.findBookByRanking(amount,pageNumber);
        if(result.isEmpty()){
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "등록된 상품이 없습니다.");
        }
        return result.get();
    }
}
