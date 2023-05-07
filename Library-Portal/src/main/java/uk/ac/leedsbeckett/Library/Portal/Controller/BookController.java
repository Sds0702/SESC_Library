package uk.ac.leedsbeckett.Library.Portal.Controller;

import com.sun.istack.NotNull;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.ac.leedsbeckett.Library.Portal.exception.BookNotFoundException;
import uk.ac.leedsbeckett.Library.Portal.service.BookService;

import java.awt.print.Book;

@RestController
public class BookController
{
    public static int length;
    private final BookService bookService;

    BookController(BookService bookService)
    {
        this.bookService = bookService;
    }
    @GetMapping("/books")
    public CollectionModel<EntityModel<Book>> all()
    {
        return bookService.getAllBooks();
    }

    @PostMapping("/books")
    ResponseEntity<?> newAccount(@RequestBody @NotNull Book newBook) {
        return bookService.createNewBook(newBook);
    }

    @GetMapping("/books/student/{BookIsbn}")
    public BookNotFoundException getStudentAccount(@PathVariable Long BookIsbn) {
        return bookService.getBookByIsbn(BookIsbn);
    }

    @GetMapping("/accounts/{isbn}")
    public BookNotFoundException one(@PathVariable Long BookIsbn) {
        return BookService.getBookByIsbn(BookIsbn);
    }

    @PutMapping("/books/{id}")
    ResponseEntity<?> editAccount(@RequestBody Book newBook, @PathVariable String id) {
        return BookService.updateOrCreateAccount(newBook, id);
    }
}