package project.shop1.feature.product.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import project.shop1.entity.Author;
import project.shop1.entity.Item;
import project.shop1.feature.product.repository.ProductRepository;


@Repository
public class ProductRepositoryImpl implements ProductRepository { //데이터베이스에 접근, 도메인 객체를 DB에 저장하고 관리

    private final EntityManager entityManager;

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public ProductRepositoryImpl(EntityManager entityManager){
        this.entityManager=entityManager;
        this.jpaQueryFactory=new JPAQueryFactory(entityManager);
    }

    /* item(책) 저장*/
    public void saveProduct(Item item){
        entityManager.persist(item);
    }

    /* 작가 저장 */
    public void saveAuthor(Author author){entityManager.persist(author); }




}
