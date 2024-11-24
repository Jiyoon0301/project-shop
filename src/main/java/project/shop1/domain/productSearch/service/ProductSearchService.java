package project.shop1.domain.productSearch.service;

import project.shop1.domain.product_refact.entity.Book;
import project.shop1.domain.productSearch.dto.SearchBookByRankingRequestDto;
import project.shop1.domain.productSearch.dto.findByKeywordAndCategoryRequestDto;

import java.util.List;

public interface ProductSearchService {

    /* 상품 검색 - 검색어, 카테고리 */
    List<Book> findByKeywordAndCategory(findByKeywordAndCategoryRequestDto findByKeywordAndCategoryRequestDto);

    /* 상품 인기순위(판매순)으로 조회*/
    List<Book> searchBookByRanking(SearchBookByRankingRequestDto searchBookByRankingRequestDto);


    }
