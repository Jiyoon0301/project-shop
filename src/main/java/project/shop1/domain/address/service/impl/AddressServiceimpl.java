package project.shop1.domain.address.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import project.shop1.domain.address.dto.request.SaveAddressRequestDto;
import project.shop1.domain.address.dto.request.SearchAddressRequestDto;
import project.shop1.domain.address.dto.response.SearchAddressResponseDto;
import project.shop1.domain.address.repository.AddressRepositoryCustom;
import project.shop1.domain.address.service.AddressService;
import project.shop1.global.exception.BusinessException;
import project.shop1.global.exception.ErrorCode;
import project.shop1.global.security.SecurityUtils;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AddressServiceimpl implements AddressService {

    private final AddressRepositoryCustom addressRepository;

    // 주소 검색
    @Override
    public List<SearchAddressResponseDto> searchAddress(SearchAddressRequestDto searchAddressRequestDto) {
        String keyword = searchAddressRequestDto.getKeyword();
        int pageNumber = searchAddressRequestDto.getPageNumber();

        URI uri = UriComponentsBuilder
                .fromUriString("https://business.juso.go.kr")
                .path("/addrlink/addrLinkApi.do")
                .queryParam("keyword", keyword)
                .queryParam("confmKey", "devU01TX0FVVEgyMDI0MDkxMzA0MDE1NzExNTA4NTg=")
                .queryParam("currentPage", pageNumber)
                .queryParam("countPerPage", 10)
                .queryParam("resultType", "json")
                .encode(Charset.forName("UTF-8"))
                .build()
                .toUri();

        log.info("uri : {}", uri);

        RestTemplate restTemplate = new RestTemplate();

        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .build();

        ResponseEntity<String> fullAddress = restTemplate.exchange(req, String.class);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(fullAddress.getBody());
            JsonNode jusoNode = rootNode.path("results").path("juso");
            List<SearchAddressResponseDto> searchAddressPairs = new ArrayList<>();

            for (JsonNode node : jusoNode) {
                String roadAddrPart1 = node.path("roadAddrPart1").asText();
                String jibunAddr = node.path("jibunAddr").asText();

                searchAddressPairs.add(new SearchAddressResponseDto(roadAddrPart1, jibunAddr));
            }
            return searchAddressPairs;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCode.RESOURCE_ACCESS_NOT_ACCEPTABLE, "fail");
        }
    }

    // 주소 저장
    @Override
    @Transactional
    public void saveAddress(Long orderId, SaveAddressRequestDto saveAddressRequestDto) {
        String account = SecurityUtils.getCurrentUsername();

        String roadAddress = saveAddressRequestDto.getRoadAddress();
        String detailedAddress = saveAddressRequestDto.getDetailedAddress();

        addressRepository.save(account, roadAddress, detailedAddress);
    }
}
