package com.github.rafaelfernandes.books.repository;

import com.github.rafaelfernandes.books.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
}
