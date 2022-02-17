package com.github.rafaelfernandes.books.repository;

import com.github.rafaelfernandes.books.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findByCategoryId(Long id);

    List<BookEntity> findByAuthorId(Long id);

    List<BookEntity> findByPublishingCompanyId(Long id);

}
