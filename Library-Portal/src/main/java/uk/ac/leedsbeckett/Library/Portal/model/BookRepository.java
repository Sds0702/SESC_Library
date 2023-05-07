package uk.ac.leedsbeckett.Library.Portal.model;

import java.awt.print.Book;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository< Book, Long > {
    Book findBookByIsbn(String bookIsbn);

    <R> R populateOutstandingBalance(Book book);

    void deleteAll();
}
