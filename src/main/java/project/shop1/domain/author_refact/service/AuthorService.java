package project.shop1.domain.author_refact.service;

import project.shop1.entity.Author;
import project.shop1.domain.author_refact.dto.RequestDto.FindAuthorByNameRequestDto;

import java.util.Optional;

public interface AuthorService {

    void addAuthor();

    Optional<Author> findAuthorByName(FindAuthorByNameRequestDto findAuthorByNameRequestDto);
}
