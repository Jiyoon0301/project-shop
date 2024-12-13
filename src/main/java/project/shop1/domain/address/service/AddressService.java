package project.shop1.domain.address.service;

import project.shop1.domain.address.dto.request.SaveAddressRequestDto;
import project.shop1.domain.address.dto.request.SearchAddressRequestDto;
import project.shop1.domain.address.dto.response.SearchAddressResponseDto;

import java.util.List;

public interface AddressService {

    // 주소 검색
    List<SearchAddressResponseDto> searchAddress(SearchAddressRequestDto searchAddressRequestDto);

    // 주소 저장
    void saveAddress(Long orderId, SaveAddressRequestDto saveAddressRequestDto);
}
