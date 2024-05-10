package project.shop1.feature.admin.controller;

import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Delete;
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
public class AuthorController { //웹 MVC의 컨트롤러 역할

    private final AuthorService authoreService;
    private final AuthorRepository authorRepository;

    /* 작가 등록 */
    //인자 productRequestDto : authorName, nation, authorIntro
    @PostMapping("/admin/author-registration")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BooleanResponse> authorRegistraion(@Validated(value = ValidationSequence.class) @RequestBody AuthorRequestDto authorRequestDto){
        authoreService.authorRegistration(authorRequestDto);

        return ResponseEntity.ok(BooleanResponse.of(true));
    }

    /* 작가 관리 페이지 */
    @PostMapping("/admin/author-management") // authorRequestDto : int pageNumber
    @PreAuthorize("hasRole('ADMIN')")
    public List<Author> authorManagement(@RequestBody AuthorManagementRequestDto authorManagementRequestDto){
        List<Author> allAuthor = authoreService.authorManagement(authorManagementRequestDto);
        return allAuthor;
    }

    /* 작가 정보 수정 */
    // authorNumberRequestDto:Long authorNumber, updateAuthorInfoRequestDto:String authorName, String nation, String authorIntro;
    @PostMapping("/admin/update-authorInfo") // validated 테스트
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BooleanResponse> updateAuthorInfo(@Validated(value = ValidationSequence.class) @RequestBody UpdateAuthorInfoRequestDto updateAuthorInfoRequestDto){
        authoreService.updateAuthorInfo(updateAuthorInfoRequestDto);
        return ResponseEntity.ok(BooleanResponse.of(true));
    }

    /* 작가 이름으로 상세 정보 조회 */
    @PostMapping("/admin/author-getDetail")
    @PreAuthorize("hasRole('ADMIN')")
    public Author authorGetDetail(@RequestBody AuthorGetDetailRequestDto authorGetDetailRequestDto){ // 인자 받기 수정
        Optional<Author> author = authoreService.authorGetDetail(authorGetDetailRequestDto);

        return author.get();
    }

    /* 작가 삭제 */
    @PostMapping("/admin/delete-author")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BooleanResponse> deleteAuthor(@Validated(value = ValidationSequence.class) @RequestBody DeleteAuthorRequestDto deleteAuthorRequestDto) {
        authoreService.deleteAuthor(deleteAuthorRequestDto);
        return ResponseEntity.ok(BooleanResponse.of(true));
    }


}
