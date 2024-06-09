package project.shop1.feature.admin.repository.repositoryImpl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import project.shop1.entity.Author;
import project.shop1.feature.admin.dto.authorDto.UpdateAuthorInfoRequestDto;
import project.shop1.feature.admin.repository.AuthorRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static project.shop1.entity.QAuthor.author;


@Repository
public class AuthorRepositoryImpl implements AuthorRepository{

    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public AuthorRepositoryImpl(EntityManager entityManager){
        this.entityManager=entityManager;
        this.jpaQueryFactory=new JPAQueryFactory(entityManager);
    }

    /* 작가 저장 */
    @Override
    public void saveAuthor(Author author){
        entityManager.persist(author);
    }

    /* 작가 관리 페이지 - 작가 전체 조회 */
    @Override
    public List<Author> findAll(int pageNumber){
        List<Author> result = jpaQueryFactory
                .selectFrom(author)
                .orderBy(author.authorNumber.asc())
                .offset((pageNumber-1)*10)
                .limit(10)
                .fetch();
        return result;

    }

    /* 작가 조회 */
    @Override
    public Optional<Author> findAuthorByName(String authorName){
        Author result = jpaQueryFactory
                .selectFrom(author)
                .where(author.authorName.eq(authorName))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<Author> findAuthorById(Long id){
        Author result = jpaQueryFactory
                .selectFrom(author)
                .where(author.id.eq(id))
                .fetchOne();
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<Author> findAuthorByAuthorNumber(Long authorNumber){
        Author result = jpaQueryFactory
                .selectFrom(author)
                .where(author.authorNumber.eq(authorNumber))
                .fetchOne();
        return Optional.ofNullable(result);
    }

    /* authorNumber 설정 */
    @Override
    public Long setAuthorNumber(){
        Long count = jpaQueryFactory
                .select(author.count())
                .from(author)
                .fetchOne();
        // 테이블이 비어있는 경우에는 count 변수에 0을 할당
        count = (count != null) ? count : 0L;
        return count;
    }

    /* 작가 정보 수정 */
    @Override
    public void updateAuthorInfo(UpdateAuthorInfoRequestDto updateAuthorInfoRequestDto) {
        Long authorNumber = updateAuthorInfoRequestDto.getAuthorNumber();

        String authorName = updateAuthorInfoRequestDto.getAuthorName();
        String nation = updateAuthorInfoRequestDto.getNation();
        String authorIntro = updateAuthorInfoRequestDto.getAuthorIntro();
        LocalDate updateDate = LocalDate.now();

        long count = jpaQueryFactory
                .update(author)
                .set(author.authorName, authorName)
                .set(author.nation, nation)
                .set(author.authorIntro, authorIntro)
                .set(author.updateDate, updateDate)
                .where(author.authorNumber.eq(authorNumber))
                .execute();

        if (count == 0){
            throw new EntityNotFoundException("작가가 존재하지 않습니다.");
        }

        Author existingAuthor = findAuthorByAuthorNumber(authorNumber).get();
        existingAuthor.setAuthorName(authorName);
        existingAuthor.setNation(nation);
        existingAuthor.setAuthorIntro(authorIntro);
        existingAuthor.setUpdateDate(updateDate);
    }

    /* 작가 삭제 */
    @Override
    public void deleteAuthor(Long authorNumber){
        Author authorToDelete = findAuthorByAuthorNumber(authorNumber).get();
        entityManager.remove(authorToDelete);
    }

}
