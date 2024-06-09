package project.shop1.feature.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.shop1.common.reponse.BooleanResponse;
import project.shop1.common.validation.ValidationSequence;
import project.shop1.entity.Author;
import project.shop1.feature.admin.dto.authorDto.*;
import project.shop1.feature.admin.repository.AuthorRepository;
import project.shop1.feature.admin.service.AuthorService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authoreService;
    private final AuthorRepository authorRepository;

    /* 작가 등록
    * AuthorRequestDto :  String authorName, String nation, String authorIntro;
    */
    @PostMapping("/admin/author-registration")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BooleanResponse> authorRegistraion(@Validated(value = ValidationSequence.class) @RequestBody AuthorRegistrationRequestDto authorRegistrationRequestDto){
        authoreService.authorRegistration(authorRegistrationRequestDto);

        return ResponseEntity.ok(BooleanResponse.of(true));
    }

    /* 작가 관리 페이지 - 전체 작가 검색
    * AuthorManagementRequestDot :
    * int pageNumber
    */
    @PostMapping("/admin/author-management")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Author> adminSearchAllAuthor(@RequestBody AdminSearchAllAuthorRequestDto adminSearchAllAuthorRequestDto){
        List<Author> allAuthor = authoreService.adminSearchAllAuthor(adminSearchAllAuthorRequestDto);
        return allAuthor;
    }

    /* 작가 정보 수정
    * UpdateAuthorInfoRequestDto :
    * Long authorNumber, String authorName, String nation, String authorIntro
    */
    @PostMapping("/admin/update-authorInfo") // validated 테스트
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BooleanResponse> updateAuthorInfo(@Validated(value = ValidationSequence.class) @RequestBody UpdateAuthorInfoRequestDto updateAuthorInfoRequestDto){
        authoreService.updateAuthorInfo(updateAuthorInfoRequestDto);
        return ResponseEntity.ok(BooleanResponse.of(true));
    }

    /* 작가 이름으로 상세 정보 조회
    * AuthorGetDetailRequestDto :
    * String authorName
    */
    @PostMapping("/admin/author-getDetail")
    @PreAuthorize("hasRole('ADMIN')")
    public Author authorGetDetail(@RequestBody AuthorGetDetailRequestDto authorGetDetailRequestDto){ // 인자 받기 수정
        Optional<Author> authorDetail = authoreService.getAuthorDetail(authorGetDetailRequestDto);

        return authorDetail.get();
    }

    /* 작가 삭제
    * DeleteAuthorByAuthorNumberRequestDto :
    * Long authorNumber
    */
    @PostMapping("/admin/delete-author")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BooleanResponse> deleteAuthorByAuthorNumber(@Validated(value = ValidationSequence.class) @RequestBody DeleteAuthorByAuthorNumberRequestDto deleteAuthorByAuthorNumberRequestDto) {
        authoreService.deleteAuthorByAuthorNumber(deleteAuthorByAuthorNumberRequestDto);
        return ResponseEntity.ok(BooleanResponse.of(true));
    }


}
