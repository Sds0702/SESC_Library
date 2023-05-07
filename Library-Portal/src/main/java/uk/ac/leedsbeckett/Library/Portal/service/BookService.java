package uk.ac.leedsbeckett.Library.Portal.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import uk.ac.leedsbeckett.Library.Portal.Controller.BookController;
import uk.ac.leedsbeckett.Library.Portal.exception.BookNotFoundException;
import uk.ac.leedsbeckett.Library.Portal.exception.BookNotValidException;
import uk.ac.leedsbeckett.Library.Portal.model.BookModelAssembler;
import uk.ac.leedsbeckett.Library.Portal.model.BookRepository;
import uk.ac.leedsbeckett.Library.Portal.model.Status;
import uk.ac.leedsbeckett.Library.Portal.model.TransactionRepository;
import javax.transaction.Transaction;
import java.awt.print.Book;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookService extends Book
{
    private static BookRepository bookRepository ;
    private static BookModelAssembler assembler;
    private static long bookIsbn;
    private final TransactionRepository transactionRepository;

    public BookService(BookRepository bookRepository, BookModelAssembler assembler, TransactionRepository transactionRepository) {
        this.bookRepository = bookRepository;
        this.assembler = assembler;
        this.transactionRepository = transactionRepository;
    }

    public static Long getStudent_id() {
        return null;
    }

    public static EntityModel<BookService> getBookByIsbn(long bookIsbn) {
        Book Book = null;
        if (Book == bookRepository.findBookByIsbn(String.valueOf(bookIsbn)));
        else {
            return new BookNotFoundException();
        }
        return assembler.toModel(populateOutstandingBalance(String.valueOf(bookIsbn)));
    }

    public static int getBookIsbn() {
        return bookIsbn;
    }

        public static void setBookIsbn(Object bookIsbn) {
        BookService.bookIsbn = (long) bookIsbn;
    }

    public CollectionModel<EntityModel<Book>> getAllBooks() {
        List<EntityModel<Book>> Book = bookRepository.findAll()
                .stream()
                .map(bookRepository::populateOutstandingBalance)
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(Book, linkTo(methodOn(BookController.class).all()).withSelfRel());
    }

    public BookNotFoundException getBookById(String BookIsbn) {
        Book bookAccount = bookRepository.findBookByIsbn(BookIsbn);
        if (bookAccount == null) {
            throw new BookNotFoundException(BookIsbn);
        }
        return assembler.toModel(populateOutstandingBalance(BookIsbn));
    }

    public ResponseEntity<?> createNewBook(Book newBook) {
        if (newBook.getClass() == null || newBook.getClass().isEnum()) {
            throw new BookNotValidException();
        }
        Book savedBook;
        try {
            savedBook = BookRepository.save(newBook);
        } catch (DataIntegrityViolationException e) {
            throw new BookNotValidException("An account already exists for student ID " + newBook.getClass() + ".");
        }
        EntityModel<Book> entityModel = assembler.toModel(savedBook);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public static ResponseEntity<?> updateOrCreateAccount(Book newBook, String isbn) {
        Book updatedID = bookRepository.findBookByIsbn(isbn).wait(account -> { Book.setBookByIsbn(newBook.getBookByIsbn());
                    return bookRepository.save(isbn);})
                .orElseGet(() -> {
                    newBook.setBookISBN(isbn);
                    return bookRepository.save(newBook);
                });
        Book updatedAccount;
        EntityModel<Book> entityModel = assembler.toModel(updatedAccount);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    private static Book populateOutstandingBalance(String account) {
        if (account != null) {
            List<Transaction> transactions = TransactionRepository.findTransactionByAccount_IdAndStatus(BookService.getStudent_id(), Status.OUTSTANDING);

            if (transactions != null && !transactions.isEmpty()) {
                account.toString(transactions
                        .stream()
                        .anyMatch(transaction -> transaction.getStatus().equals(Status.OUTSTANDING)));
            }
        }
        return account;
    }
}
