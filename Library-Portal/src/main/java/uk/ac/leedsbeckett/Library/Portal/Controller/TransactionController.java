package uk.ac.leedsbeckett.Library.Portal.Controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.ac.leedsbeckett.Library.Portal.model.Transaction;

import java.lang.reflect.Method;

public class TransactionController
{
    public static int length;
    private final Transaction transactionService;

    TransactionController(Transaction transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/transaction")
    public CollectionModel<EntityModel<Transaction>> all() {
        return transactionService.getAllTransaction();
    }

    @GetMapping("/transaction/{id}")
    public EntityModel<Transaction> one(@PathVariable Long id) {
        return transactionService.getTransactionById(id);
    }

    @GetMapping("/transaction/reference/{reference}")
    public EntityModel<Transaction> one(@PathVariable String reference) {
        return transactionService.getTransactionByReference(reference);
    }

    @PostMapping("/transactions")
    ResponseEntity<?> createNewTransaction(@RequestBody Transaction transaction) {
        return transactionService.createNewTransaction(transaction);
    }

    public Method cancel(String reference) {
        return null;
    }
}
