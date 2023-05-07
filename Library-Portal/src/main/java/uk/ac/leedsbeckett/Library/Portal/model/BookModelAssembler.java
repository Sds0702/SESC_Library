package uk.ac.leedsbeckett.Library.Portal.model;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import uk.ac.leedsbeckett.Library.Portal.Controller.BookController;
import uk.ac.leedsbeckett.Library.Portal.exception.BookNotFoundException;
import uk.ac.leedsbeckett.Library.Portal.exception.BookNotValidException;
import uk.ac.leedsbeckett.Library.Portal.service.BookService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static uk.ac.leedsbeckett.Library.Portal.service.BookService.getStudent_id;

@Component
public class BookModelAssembler implements RepresentationModelAssembler< Book, EntityModel<Book>>
{
    private static int Book;
    private BookService populateOutstandingBalance;

    public static EntityModel<BookService> toModel(java.awt.print.Book book)
    {
        if (getStudent_id() == 0) {
            throw new BookNotValidException();
        } else if (getStudent_id() == null) {
            throw new BookNotValidException();
        }
      return EntityModel.of((BookService) book,linkTo(methodOn(BookController.class).getStudentAccount(getStudent_id())).withSelfRel(),
                linkTo(methodOn(BookController.class).all()).withRel("book"));
    }

    public static void setBook(int book) {
        Book = book;
    }

    @Override
    public EntityModel<uk.ac.leedsbeckett.Library.Portal.model.Book> toModel(uk.ac.leedsbeckett.Library.Portal.model.Book entity) {
        return null;
    }

    public BookNotFoundException toModel(BookService populateOutstandingBalance) {
        this.populateOutstandingBalance = populateOutstandingBalance;
        return null;
    }

        /*public EntityModel<uk.ac.leedsbeckett.Library.Portal.model.Book> toModel(Book populateOutstandingBalance) {
            boolean Book;
            if (Book != null) {
            List<TransactionService> transactions = TransactionRepository.findTransactionByAccount_IdAndStatus(Long.valueOf(getBookIsbn()), Status.OUTSTANDING);

            if (transactions != null && !transactions.isEmpty()) {
                Book.setHasOutstandingBalance(transactions.stream().anyMatch(transaction -> transaction.getStatus().equals(Status.OUTSTANDING)));
            }
        }
        return Book;
    }*/
}
