package com.github.rafaelfernandes.books.repository;

import com.github.rafaelfernandes.books.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
