package uk.ac.leedsbeckett.Library.Portal.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FineRepository extends JpaRepository<Transaction, Long>
{
    List<Transaction> findTransactionByAccount_IsbnAndStatus(Long accountId, Status status);

    Transaction findTransactionByReference(String reference);
}