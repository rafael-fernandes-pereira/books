package com.github.rafaelfernandes.books.dto;

import com.github.rafaelfernandes.books.entity.BookEntity;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
public class BookDto {

    private Long id;
    private String name;
    private String author;
    private String category;
    private String publishingCompany;
    private BigDecimal price;

    private BookDto(Long id, String name, String author, String category, String publishingCompany, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.category = category;
        this.publishingCompany = publishingCompany;
        this.price = price;
    }

    public static BookDto of(BookEntity book){
        return new BookDto(
                book.getId(),
                book.getName(),
                book.getAuthor().getName(),
                book.getCategory().getName(),
                book.getPublishingCompany().getName(),
                book.getPrice()
        );
    }

    public static List<BookDto> of(List<BookEntity> books){

        List<BookDto> list = new ArrayList<>();

        books.stream().forEach(book -> list.add(BookDto.of(book)));

        return list;

    }

}
