package project.shop1.domain.productSearch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.common.validation.ValidationSequence;
import project.shop1.entity.Book;
import project.shop1.domain.productSearch.dto.SearchBookByRankingRequestDto;
import project.shop1.domain.productSearch.dto.findByKeywordAndCategoryRequestDto;
import project.shop1.domain.productSearch.service.ProductSearchService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductSearchController {

    private final ProductSearchService productSearchService;


    /* 상품 검색 - 검색어, 카테고리 */
    @PostMapping("/search/find-by-keyword-and-category") //searchBookByTitleRequestDto : String criteria, String keyword, String category, int amount, int pageNumber
    public List<Book> findByKeywordAndCategory(@Validated(value = ValidationSequence.class) @RequestBody findByKeywordAndCategoryRequestDto findByKeywordAndCategoryRequestDto){
        List<Book> result = productSearchService.findByKeywordAndCategory(findByKeywordAndCategoryRequestDto);
        return result;
    }

    /* 인기순위(판매순) 상품 조회 */
    @PostMapping("/search/search-book-by-ranking") //searchBookByRankingRequestDto : int amount, int pageNumber
    public List<Book> searchBookByRanking(@Validated(value = ValidationSequence.class) @RequestBody SearchBookByRankingRequestDto searchBookByRankingRequestDto){
        List<Book> result = productSearchService.searchBookByRanking(searchBookByRankingRequestDto);
        return result;
    }

}
