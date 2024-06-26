package project.shop1.feature.admin.service;

import project.shop1.entity.Author;
import project.shop1.feature.admin.dto.authorDto.*;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    /* 작가 등록 */
    void authorRegistration(AuthorRegistrationRequestDto authorRegistrationRequestDto);

    /* 작가 관리 페이지 - 전체 조회 */
    List<Author> adminSearchAllAuthor(AdminSearchAllAuthorRequestDto adminSearchAllAuthorRequestDto);



        /* 작가 정보 수정 */
    void updateAuthorInfo(UpdateAuthorInfoRequestDto updateAuthorInfoRequestDto);


    /* 작가 세부 정보 */
    Optional<Author> getAuthorDetail(AuthorGetDetailRequestDto authorGetDetailRequestDto);

    /* 작가 삭제 */
    void deleteAuthorByAuthorNumber(DeleteAuthorByAuthorNumberRequestDto deleteAuthorByAuthorNumberRequestDto);



    }