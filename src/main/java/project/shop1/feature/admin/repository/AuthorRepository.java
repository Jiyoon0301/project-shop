package project.shop1.feature.admin.repository;

import project.shop1.entity.Author;
import project.shop1.feature.admin.dto.authorDto.UpdateAuthorInfoRequestDto;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository{

    /* 작가 등록 */
    void saveAuthor(Author author);

    /* 작가 관리 페이지 - 작가 전체 조회 */
    List<Author> findAll(int pageNumber);


        /* 작가 조회 */
    Optional<Author> findAuthorByName(String authorName);
    Optional<Author> findAuthorById(Long id);
    Optional<Author> findAuthorByAuthorNumber(Long authorNumber);

    /* authorNumber 설정 */
    Long numberOfAuthor();

    /* 작가 정보 수정 */
    void updateAuthorInfo(UpdateAuthorInfoRequestDto updateAuthorInfoRequestDto);

    /* 작가 목록 */
//    List<AuthorRequestDto> authorGetList(Criteria cri);

    /* 작가 삭제 */
    void deleteAuthor(Long authorNumber);


    }