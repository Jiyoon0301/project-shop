package project.shop1.domain.main.repository;

import project.shop1.domain.product_refact.entity.Book;

import java.util.List;

public interface MainRepository {

    /* 상품 평점 높은 순으로 찾기 */
    List<Book> findProductsByRanking(int page);

    }
