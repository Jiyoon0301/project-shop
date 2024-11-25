package project.shop1.domain.product.author_needRefactor.service;

import project.shop1.domain.product.entity.Author;
import project.shop1.domain.product.author_needRefactor.dto.RequestDto.FindAuthorByNameRequestDto;

import java.util.Optional;

public interface AuthorService {

    void addAuthor();

    Optional<Author> findAuthorByName(FindAuthorByNameRequestDto findAuthorByNameRequestDto);
}
