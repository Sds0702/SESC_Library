package uk.ac.leedsbeckett.Library.Portal.model;

import java.util.List;
public interface TransactionRepository {
    List<Transaction> findTransactionByAccount_IdAndStatus(Long accountId, Status status);
    Transaction findTransactionByReference (String reference);
}
