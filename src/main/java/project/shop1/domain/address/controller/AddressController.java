package project.shop1.domain.address.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.shop1.domain.address.dto.request.SaveAddressRequestDto;
import project.shop1.domain.address.dto.request.SearchAddressRequestDto;
import project.shop1.domain.address.dto.response.SearchAddressResponseDto;
import project.shop1.domain.address.service.AddressService;
import project.shop1.global.util.reponse.BooleanResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    /**
     * 주소 조회
     * @param searchAddressRequestDto: String keyword, int pageNumber
     * @return
     */
    @PostMapping("/search")
    @PreAuthorize("hasRole('USER')")
    public List<SearchAddressResponseDto> searchAddress(@RequestBody SearchAddressRequestDto searchAddressRequestDto) {
        List<SearchAddressResponseDto> result = addressService.searchAddress(searchAddressRequestDto);
        return result;
    }

    /**
     * 주소 저장 - 도로명 + 상세주소
     * @param saveAddressRequestDto: String roadAddress, String detailedAddress
     * @return
     */
    @PostMapping("/save")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BooleanResponse> saveAddress( @RequestParam Long orderId, @RequestBody SaveAddressRequestDto saveAddressRequestDto) {
        addressService.saveAddress(orderId, saveAddressRequestDto);
        return ResponseEntity.ok(BooleanResponse.of(true));
    }
}
