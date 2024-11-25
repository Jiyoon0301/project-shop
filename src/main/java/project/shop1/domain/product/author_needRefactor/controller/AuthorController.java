package project.shop1.domain.product.author_needRefactor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.global.util.reponse.BooleanResponse;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    /* 작가 등록 */
    @PostMapping("/authors")
    public ResponseEntity<BooleanResponse> addAuthor(){
        return null;
    }
}
