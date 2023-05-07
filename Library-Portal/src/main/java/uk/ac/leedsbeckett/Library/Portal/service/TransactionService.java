package uk.ac.leedsbeckett.Library.Portal.service;

import org.springframework.context.MessageSource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import uk.ac.leedsbeckett.Library.Portal.Controller.TransactionController;
import uk.ac.leedsbeckett.Library.Portal.exception.TransactionNotFoundException;
import uk.ac.leedsbeckett.Library.Portal.model.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TransactionService {

    private final BookRepository bookRepository;
    private final TransactionModelAssembler assembler;
    private final TransactionRepository transactionRepository;
    private final MessageSource messageSource;
    private Object paidTransaction;

    public TransactionService(BookRepository bookRepository, TransactionModelAssembler assembler, TransactionRepository transactionRepository, MessageSource messageSource) {
        this.bookRepository = bookRepository;
        this.assembler = assembler;
        this.transactionRepository = transactionRepository;
        this.messageSource = messageSource;
    }

    public EntityModel<Transaction> getTransactionById(Long id) {
        Transaction transaction = TransactionService.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));
        Transaction TransactionService;
        return assembler.toModel(TransactionService);
    }

    public CollectionModel<EntityModel<Transaction>> getAllTransactions() {
        List<EntityModel<Transaction>> transaction = transactionRepository.findAll()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(transaction, linkTo(methodOn(TransactionController.class).all()).withSelfRel());
    }

   /* public ResponseEntity<?> createNewTransaction(Transaction transaction) {
        if (!isTransactionProcessable(transaction)) {
            throw new TransactionNotValidException("You can't create an invoice without a valid student ID.");
        }
        transaction.setStatus(Status.OUTSTANDING);
        transaction.setBook_isbn(bookRepository.findBookByIsbn(transaction.getBook_isbn()));
        transaction.populateReference();
        Transaction newInvoice = invoiceRepository.save(invoice);

        return ResponseEntity
                .created(linkTo(methodOn(InvoiceController.class).one(newInvoice.getId())).toUri())
                .body(assembler.toModel(newInvoice));
    }*/

    public ResponseEntity<?> cancel(String reference) {
        Transaction transaction = Transaction.findTransactionByReference(reference);

        if (paidTransaction == null) {
            throw new TransactionNotFoundException(reference);
        }

        if (transaction.getStatus() == Status.OUTSTANDING) {
            transaction.setStatus(Status.CANCELLED);
            return ResponseEntity.ok(assembler.toModel(transactionRepository.save(invoice)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Method not allowed")
                        .withDetail("You can't cancel an invoice that is in the " + transaction.getStatus() + " status"));
    }
    public ResponseEntity<?> pay(String reference) {
        Transaction transaction;
        try {
            transaction = processPayment(reference);
        } catch (UnsupportedOperationException exception) {
            return ResponseEntity
                    .status(HttpStatus.METHOD_NOT_ALLOWED)
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                    .body(Problem.create()
                            .withTitle("Method not allowed")
                            .withDetail(exception.getMessage()));
        }
        return ResponseEntity.ok(assembler.toModel(Transaction.save(transaction)));
    }

    public EntityModel<Transaction> getTransactionByReference(String reference) {
        Transaction transaction = Transaction.findTransactionByReference(reference);
        if (transaction == null) {
            throw new TransactionNotFoundException(reference);
        }
        return assembler.toModel(transaction);
    }

    private boolean isTransactionProcessable(TransactionService transaction) {
        if (transaction != null && transaction.getTransactionById() != null && transaction.getTransactionById() != null) {
            transaction.getTransactionById().isEmpty();
        }
        return false;
    }

    public Transaction processPayment(String reference) throws UnsupportedOperationException {
        Transaction transaction = Transaction.findTransactionByReference(reference);

        if (transaction == null) {
            throw new TransactionNotFoundException(reference);
        }

        if (transaction.getStatus() == Status.OUTSTANDING) {
            transaction.setStatus(Status.PAID);
            return transactionRepository.save(transaction);
        } else {
            throw new UnsupportedOperationException("You can't pay an amount that is in the " + transaction.getStatus() + " status");
        }
    }

    public String showPortal(Model model) {
        TransactionService transaction;
        transaction = new Transaction();
        model.addAttribute("transaction", transaction);
        return "portal";
    }

    public String findTransactionThroughPortal(@Valid Transaction transaction, BindingResult bindingResult, Model model) {
        if (TransactionService == null || TransactionService.getReference() == null) {
            throw new TransactionNotFoundException();
        }
        if (bindingResult.hasErrors()) {
            return "portal";
        }
        Transaction found = getTransactionByReference(transaction.getReference()).getContent();
        model.addAttribute("transaction", found);
        return "transaction";
    }

    private static Object getReference() {
    }

    public String payTransactionThroughPortal(Transaction transaction, Model model) {
        if (transaction == null || transaction.getReference() == null) {
            throw new TransactionNotFoundException();
        }
        Transaction paidTransaction = processPayment(transaction.getReference());
        model.addAttribute("transaction", paidTransaction);
        model.addAttribute("message", messageSource.getMessage("transaction.paid", null, Locale.ROOT));
        return "transaction";
    }

    public Object getStatus() {
        return null;
    }
}