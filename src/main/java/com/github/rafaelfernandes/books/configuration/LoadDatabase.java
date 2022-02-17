package com.github.rafaelfernandes.books.configuration;

import com.github.rafaelfernandes.books.entity.AuthorEntity;
import com.github.rafaelfernandes.books.entity.BookEntity;
import com.github.rafaelfernandes.books.entity.CategoryEntity;
import com.github.rafaelfernandes.books.entity.PublishingCompanyEntity;
import com.github.rafaelfernandes.books.repository.AuthorRepository;
import com.github.rafaelfernandes.books.repository.BookRepository;
import com.github.rafaelfernandes.books.repository.CategoryRepository;
import com.github.rafaelfernandes.books.repository.PublishingCompanyRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(AuthorRepository authorRepository, CategoryRepository categoryRepository, BookRepository bookRepository, PublishingCompanyRepository publishingCompanyRepository){

        return  args -> {

            var categories = new HashMap<String, CategoryEntity>();
            var authors = new HashMap<String, AuthorEntity>();
            var publishingCompanies = new HashMap<String, PublishingCompanyEntity>();

            BufferedReader fileReader = new BufferedReader(new FileReader("books.csv"));
            CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {

                var authorName = String.valueOf(csvRecord.get("Autor"));
                if (null == authors.get(authorName)){
                    authors.put(authorName, authorRepository.save(new AuthorEntity((authorName))));
                }

                var categoryName = String.valueOf(csvRecord.get("Categoria"));
                if (null == categories.get(categoryName)){
                    categories.put(categoryName, categoryRepository.save(new CategoryEntity((categoryName))));
                }

                var publishingCompanyName = String.valueOf(csvRecord.get("Editora"));
                if (null == publishingCompanies.get(publishingCompanyName)){
                    publishingCompanies.put(publishingCompanyName, publishingCompanyRepository.save(new PublishingCompanyEntity((publishingCompanyName))));
                }

                var bookName = String.valueOf(csvRecord.get("Livro"));
                var price = BigDecimal.valueOf(Double.valueOf(String.valueOf(csvRecord.get("Preco"))));

                bookRepository.save(
                        new BookEntity(
                                bookName,
                                authors.get(authorName),
                                categories.get(categoryName),
                                publishingCompanies.get(publishingCompanyName),
                                price
                        )
                );
            }

        };

    }

}
