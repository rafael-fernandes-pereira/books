package com.github.rafaelfernandes.books.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "book")
public class BookEntity {

    public BookEntity(String name, AuthorEntity author, CategoryEntity category, PublishingCompanyEntity publishingCompany, BigDecimal price){
        this.name = name;
        this.author = author;
        this.category = category;
        this.publishingCompany = publishingCompany;
        this.price = price;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private AuthorEntity author;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private CategoryEntity category;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "publishing_company_id", referencedColumnName = "id")
    private PublishingCompanyEntity publishingCompany;

    @Column(nullable = false)
    private BigDecimal price;

}
