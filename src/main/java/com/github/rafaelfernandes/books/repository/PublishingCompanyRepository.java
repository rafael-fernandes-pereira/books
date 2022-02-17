package com.github.rafaelfernandes.books.repository;

import com.github.rafaelfernandes.books.entity.PublishingCompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublishingCompanyRepository extends JpaRepository<PublishingCompanyEntity, Long> {
}
