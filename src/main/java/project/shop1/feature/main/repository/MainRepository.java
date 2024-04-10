package project.shop1.feature.main.repository;

import project.shop1.entity.Book;

import java.util.List;

public interface MainRepository {

    /* 상품 평점 높은 순으로 찾기 */
    List<Book> findProductsByRating(int page);

    }
