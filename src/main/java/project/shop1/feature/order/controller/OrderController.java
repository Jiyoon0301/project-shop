//package project.shop1.feature.order.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//import project.shop1.common.reponse.BooleanResponse;
//import project.shop1.common.validation.ValidationSequence;
//import project.shop1.feature.login.dto.LoginRequestDto;
//import project.shop1.feature.order.service.OrderServicee;
//
//@RestController
//@RequiredArgsConstructor
//public class OrderController {
//
//    private final OrderServicee orderServicee;
//
//    @PostMapping("/order")
//    public ResponseEntity<BooleanResponse> orderByUser (@Validated(value= ValidationSequence.class) LoginRequestDto loginRequestDto){
//        orderService.loginUser(loginRequestDto,httpServletRequest);
//        return new ResponseEntity<>(new ResponseDto("로그인이 성공적으로 완료되었습니다."), new HttpHeaders(), HttpStatus.OK);
//    }
//}
