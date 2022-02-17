package com.github.rafaelfernandes.books.controller;

import com.github.rafaelfernandes.books.dto.BookDto;
import com.github.rafaelfernandes.books.entity.AuthorEntity;
import com.github.rafaelfernandes.books.entity.BookEntity;
import com.github.rafaelfernandes.books.entity.CategoryEntity;
import com.github.rafaelfernandes.books.entity.PublishingCompanyEntity;
import com.github.rafaelfernandes.books.repository.AuthorRepository;
import com.github.rafaelfernandes.books.repository.BookRepository;
import com.github.rafaelfernandes.books.repository.CategoryRepository;
import com.github.rafaelfernandes.books.repository.PublishingCompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;


    @Autowired
    private PublishingCompanyRepository publishingCompanyRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    @GetMapping("/book/{id}")
    public BookDto getId(@PathVariable Long id) throws Exception {

        var book = bookRepository.findById(id).orElseThrow();
        return BookDto.of(book);

    }

    @GetMapping("/book/all")
    public List<BookDto> getAll() throws Exception {
        var books = bookRepository.findAll();
        return BookDto.of(books);
    }

    @GetMapping("/book/category/{id}")
    public List<BookDto> findBooksByCategory(@PathVariable Long id) {
        var books = bookRepository.findByCategoryId(id);
        return BookDto.of(books);
    }

    @GetMapping("/book/publishingCompany/{id}")
    public List<BookDto> findBooksByPublishingCompany(@PathVariable Long id) {
        var books = bookRepository.findByPublishingCompanyId(id);
        return BookDto.of(books);
    }

    @GetMapping("/book/author/{id}")
    public List<BookDto> findBooksByAuthor(@PathVariable Long id) {
        var books = bookRepository.findByAuthorId(id);
        return BookDto.of(books);
    }

    @GetMapping("/author/all")
    public List<AuthorEntity> getAllAuthor() {
        return authorRepository.findAll();
    }

    @GetMapping("/publishingCompany/all")
    public List<PublishingCompanyEntity> getAllPublishingCompany() {
        return publishingCompanyRepository.findAll();
    }

    @GetMapping("/category/all")
    public List<CategoryEntity> getAllCategory() {
        return categoryRepository.findAll();
    }

    @PostMapping("/applyNewPrice")
    public void applyNewPrice(@RequestBody ApplyNewPriceRequest request){

        List<BookEntity> list = new ArrayList<BookEntity>();

        TypeChange typeChange = TypeChange.valueOf(request.getTypeOfApply().toUpperCase());
        Long id = request.getId();
        var percentage = new BigDecimal(request.getPercentage()).divide(new BigDecimal(100));

        switch (typeChange){
            case AUTHOR: list = this.bookRepository.findByAuthorId(id); break;
            case CATEGORY: list = this.bookRepository.findByCategoryId(id); break;
            case PUBLISHING_COMPANY: list = this.bookRepository.findByPublishingCompanyId(id); break;
        }

        list.stream().forEach(book -> {

            var newPrice = book.getPrice();
            var pecentageValue = newPrice.multiply(percentage);

            newPrice = newPrice.subtract(pecentageValue);
            newPrice = newPrice.setScale(2, RoundingMode.HALF_EVEN);

            book.setPrice(newPrice);

        });

        bookRepository.saveAll(list);
    }

}
