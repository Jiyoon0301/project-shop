package project.shop1.domain.product.author_needRefactor.repository.AuthorRepositoryImpl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import project.shop1.domain.product.entity.Author;
import project.shop1.domain.product.author_needRefactor.repository.AuthorRepository;
import static project.shop1.entity.QAuthor.author;

import java.util.Optional;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {

    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public AuthorRepositoryImpl(EntityManager entityManager){
        this.entityManager=entityManager;
        this.jpaQueryFactory=new JPAQueryFactory(entityManager);
    }

    @Override
    public Optional<Author> findAuthorByName(String authorName){
        Author result = jpaQueryFactory
                .selectFrom(author)
                .where(author.name.eq(authorName))
                .fetchOne();
        return Optional.ofNullable(result);
    }
}
