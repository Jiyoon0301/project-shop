package project.shop1.feature.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.common.reponse.BooleanResponse;
import project.shop1.common.validation.ValidationSequence;
import project.shop1.feature.admin.dto.authorDto.AuthorRequestDto;

@RestController
@RequiredArgsConstructor
public class adminController {

    @PostMapping("/admin/main")
    public ResponseEntity<BooleanResponse> authorRegistraion(){

        return ResponseEntity.ok(BooleanResponse.of(true));
    }
}
