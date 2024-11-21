package project.shop1.domain.author_refact.repository;

import project.shop1.entity.Author;

import java.util.Optional;

public interface AuthorRepository {
    Optional<Author> findAuthorByName(String authorName);
}