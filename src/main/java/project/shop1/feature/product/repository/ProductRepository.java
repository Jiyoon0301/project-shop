package project.shop1.feature.product.repository;

import project.shop1.entity.Author;
import project.shop1.entity.Item;

public interface ProductRepository {

    void saveProduct(Item item);

    /* 작가 저장 */
    void saveAuthor(Author author);
}
