package project.shop1.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import project.shop1.feature.join.dto.JoinRequestDto;

import java.net.URI;
import java.nio.charset.Charset;

@Service
@Slf4j
public class AddressService {

    public String searchAddress(String keyword){

        URI uri = UriComponentsBuilder
                .fromUriString("https://business.juso.go.kr")
                .path("/addrlink/addrLinkApi.do")
                .queryParam("keyword",keyword)
                .queryParam("confmKey", "devU01TX0FVVEgyMDI0MDIyNjIwMTE1OTExNDU0NzY=")
                .queryParam("currentPage", 1)
                .queryParam("countPerPage", 1)
                .queryParam("resultType", "json")
                .encode(Charset.forName("UTF-8"))
                .build()
                .toUri();

        log.info("uri : {}", uri);

        RestTemplate restTemplate = new RestTemplate();

        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .build();

        ResponseEntity<String> result = restTemplate.exchange(req, String.class);

        return result.getBody();
    }

}
