package project.shop1.feature.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.common.reponse.BooleanResponse;
import project.shop1.common.validation.ValidationSequence;
import project.shop1.entity.UserEntity;
import project.shop1.feature.admin.dto.userDto.AdminSearchUserByAccountRequestDto;
import project.shop1.feature.admin.dto.userDto.UserRequestDto;
import project.shop1.feature.admin.service.UserManagementService;

@RestController
@RequiredArgsConstructor
public class UserManagementController {

    private final UserManagementService userManagementService;

    /* 회원 관리 */
    @PostMapping("/admin/user-management")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BooleanResponse> UserManagement(@Validated(value = ValidationSequence.class) @RequestBody UserRequestDto userRequestDto){

//        productService.productRegistration(productRequestDto);

        return ResponseEntity.ok(BooleanResponse.of(true));
    }

    /* 회원 검색 - account, 한 명
    * SearchUserByAccountRequestDto :
    * String account
    * */
    @PostMapping("/admin/search-user-by-account")
    @PreAuthorize("hasRole('ADMIN')")
    public UserEntity adminSearchUserByAccount(@Validated(value = ValidationSequence.class) @RequestBody AdminSearchUserByAccountRequestDto adminSearchUserByAccountRequestDto){
        UserEntity searchUserByAccount = userManagementService.adminSearchUserByAccount(adminSearchUserByAccountRequestDto);

        return searchUserByAccount;
    }


}
