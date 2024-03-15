package project.shop1.feature.product.service;

import project.shop1.feature.join.dto.JoinRequestDto;
import project.shop1.feature.product.dto.AuthorRequestDto;
import project.shop1.feature.product.dto.ProductRequestDto;

public interface ProductService {

    void productRegistration(ProductRequestDto productRequestDto);

    void productManagement(JoinRequestDto joinRequestDto);

    void authorRegistration(AuthorRequestDto authorRequestDto);


    void authorManagement(JoinRequestDto joinRequestDto);





    }