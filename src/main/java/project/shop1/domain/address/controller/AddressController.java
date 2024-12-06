package project.shop1.domain.address.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
     * 주소 조회 ****************
     * @param searchAddressRequestDto
     * @return
     */
    @PostMapping("/search") // String keyword, int pageNumber
    @PreAuthorize("hasRole('USER')")
    public List<SearchAddressResponseDto> searchAddress(@RequestBody SearchAddressRequestDto searchAddressRequestDto) {
        List<SearchAddressResponseDto> result = addressService.searchAddress(searchAddressRequestDto);
        return result;
    }

    /**
     * 주소 저장 - 도로명 + 상세주소
     * @param saveAddressRequestDto
     * @return
     */
    @PostMapping("/save") //SaveAddressRequestDto : String roadAddress, String detailedAddress
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BooleanResponse> saveAddress(@RequestBody SaveAddressRequestDto saveAddressRequestDto) {
        addressService.saveAddress(saveAddressRequestDto);
        return ResponseEntity.ok(BooleanResponse.of(true));
    }
}
