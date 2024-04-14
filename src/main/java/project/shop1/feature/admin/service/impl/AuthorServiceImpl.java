package project.shop1.feature.admin.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.common.exception.BusinessException;
import project.shop1.common.exception.ErrorCode;
import project.shop1.entity.Author;
import project.shop1.feature.admin.dto.authorDto.*;
import project.shop1.feature.admin.repository.AuthorRepository;
import project.shop1.feature.admin.service.AuthorService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AuthorServiceImpl implements AuthorService { //핵심 비즈니스 로직 구현

    private final AuthorRepository authorRepository;

    /* 작가 등록 메서드 */
    @Transactional
    @Override
    public void authorRegistration(AuthorRequestDto authorRequestDto) {
        Long authorNumber = authorRepository.numberOfAuthor() + 1;
        String authorName = authorRequestDto.getAuthorName();
        String nation = authorRequestDto.getNation();
        String authorIntro = authorRequestDto.getAuthorIntro();
        LocalDate regDate = LocalDate.now();

        Author author = Author.builder()
                .authorNumber(authorNumber)
                .authorName(authorName)
                .nation(nation)
                .authorIntro(authorIntro)
                .regDate(regDate)
                .updateDate(regDate)
                .build();

        authorRepository.saveAuthor(author);
    }

    /* 작가 관리 페이지 */
    @Override
    public List<Author> authorManagement(AuthorManagementRequestDto authorManagementRequestDto){
        int pageNumber = authorManagementRequestDto.getPageNumber();
        List<Author> allAuthor = authorRepository.findAll(pageNumber);

        if(allAuthor.isEmpty()){
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,"등록된 작가가 없습니다.");
        }
        log.info("(service)authorManagement().....");
        return allAuthor;
    }

    /* 작가 정보 수정 */
    @Override
    @Transactional
    public void updateAuthorInfo(UpdateAuthorInfoRequestDto updateAuthorInfoRequestDto) {
        authorRepository.updateAuthorInfo(updateAuthorInfoRequestDto);
//        log.info("(service) updateAuthorInfo......" + authorRequestDto);

    }

    /* 작가 상세 정보 */
    @Override
    public Optional<Author> authorGetDetail(AuthorGetDetailRequestDto authorGetDetailRequestDto) {
        String authorName = authorGetDetailRequestDto.getAuthorName();
        Optional<Author> findAuthor = authorRepository.findAuthorByName(authorName);
        if (findAuthor.isEmpty()){
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "존재하지 않는 작가입니다.");
        }
        return findAuthor;

    }

    /* 작가 삭제 */
    @Override
    @Transactional
    public void deleteAuthor(DeleteAuthorRequestDto deleteAuthorRequestDto) {
        Long authorNumber = deleteAuthorRequestDto.getAuthorNumber();
        authorRepository.deleteAuthor(authorNumber);
    }

}
