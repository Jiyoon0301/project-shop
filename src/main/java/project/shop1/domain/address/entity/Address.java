package project.shop1.domain.address.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String roadAddress;
    private String detailedAddress;
//    private String street;
//    private String city;
//    private String state;
//    private String zipCode;
}
