package project.shop1.domain.product.productSearch_needRefactor.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.domain.product.productSearch_needRefactor.repository.ProductSearchRepository;
import project.shop1.global.exception.BusinessException;
import project.shop1.global.exception.ErrorCode;
import project.shop1.domain.product.entity.Book;
import project.shop1.domain.product.productSearch_needRefactor.dto.SearchBookByRankingRequestDto;
import project.shop1.domain.product.productSearch_needRefactor.dto.findByKeywordAndCategoryRequestDto;
import project.shop1.domain.product.productSearch_needRefactor.service.ProductSearchService;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductSearchServiceImpl implements ProductSearchService {
    private final ProductSearchRepository productSearchRepository;

    // 캐시를 위한 HashMap (검색어 + 카테고리 조합을 키로, 결과 리스트를 값으로)
    private final HashMap<String, List<Book>> searchCache = new HashMap<>();


    /* 상품 검색 - 검색어, 카테고리 */
    @Override
    public List<Book> findByKeywordAndCategory(findByKeywordAndCategoryRequestDto findByKeywordAndCategoryRequestDto) {
        String criteria = findByKeywordAndCategoryRequestDto.getCriteria();
        String keyword = findByKeywordAndCategoryRequestDto.getKeyword();
        String category = findByKeywordAndCategoryRequestDto.getCategory();
        int amount = findByKeywordAndCategoryRequestDto.getAmount();
        int pageNumber = findByKeywordAndCategoryRequestDto.getPageNumber();

        // 캐시 키 생성 (검색어 + 카테고리)
        String cacheKey = criteria + ":" + keyword + ":" + category + ":" + amount + ":" + pageNumber;

        // 캐시에서 먼저 검색 결과 확인
        if (searchCache.containsKey(cacheKey)) {
            return searchCache.get(cacheKey);
        }

        // 캐시에 없으면 DB 조회
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
