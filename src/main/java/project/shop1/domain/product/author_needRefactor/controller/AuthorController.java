package project.shop1.domain.product.author_needRefactor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.domain.product.author_needRefactor.service.AuthorService;
import project.shop1.global.util.reponse.BooleanResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class AuthorController {

    private final AuthorService authorService;

    /* 작가 등록 */
    @PostMapping("/add")
    public ResponseEntity<AddAuthorResponseDto> addAuthor(){
        return null;
    }
}
