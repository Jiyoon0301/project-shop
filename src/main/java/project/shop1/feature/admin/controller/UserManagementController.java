package project.shop1.feature.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.common.reponse.BooleanResponse;
import project.shop1.common.validation.ValidationSequence;
import project.shop1.feature.admin.dto.UserRequestDto;
import project.shop1.feature.admin.repository.ProductRepository;
import project.shop1.feature.admin.service.ProductService;

@RestController
@RequiredArgsConstructor
public class UserManagementController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    /* 회원 관리 */
    @PostMapping("/user-management")
    public ResponseEntity<BooleanResponse> UserManagement(@Validated(value = ValidationSequence.class) @RequestBody UserRequestDto userRequestDto){

//        productService.productRegistration(productRequestDto);

        return ResponseEntity.ok(BooleanResponse.of(true));
    }


}
