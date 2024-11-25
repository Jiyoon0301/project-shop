package project.shop1.domain.product.author_needRefactor.service.AuthorServiceImpl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.domain.product.author_needRefactor.service.AuthorService;
import project.shop1.domain.product.entity.Author;
import project.shop1.domain.product.author_needRefactor.dto.RequestDto.FindAuthorByNameRequestDto;
import project.shop1.domain.product.author_needRefactor.repository.AuthorRepository;

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
