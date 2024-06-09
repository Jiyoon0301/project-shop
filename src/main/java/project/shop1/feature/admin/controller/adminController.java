package project.shop1.feature.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.common.reponse.BooleanResponse;

@RestController
@RequiredArgsConstructor
public class adminController {

    /* 작가 등록 */
    @PostMapping("/admin/main")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BooleanResponse> authorRegistraion(){

        return ResponseEntity.ok(BooleanResponse.of(true));
    }
}
