package project.shop1.domain.author_refact.service.AuthorServiceImpl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.domain.product_refact.entity.Author;
import project.shop1.domain.author_refact.dto.RequestDto.FindAuthorByNameRequestDto;
import project.shop1.domain.author_refact.repository.AuthorRepository;
import project.shop1.domain.author_refact.service.AuthorService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public void addAuthor() {

    }

    /* 작가 검색
    * 이름으로 검색
    */
    @Override
    public Optional<Author> findAuthorByName(FindAuthorByNameRequestDto findAuthorByNameRequestDto){
        String authorName = findAuthorByNameRequestDto.getName();
        return authorRepository.findAuthorByName(authorName);
    }

}
