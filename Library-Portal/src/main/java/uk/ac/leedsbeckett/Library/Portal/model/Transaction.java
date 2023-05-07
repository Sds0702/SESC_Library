package uk.ac.leedsbeckett.Library.Portal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import uk.ac.leedsbeckett.Library.Portal.constants.constants;
import uk.ac.leedsbeckett.Library.Portal.service.TransactionService;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
@Data
public class Transaction extends TransactionService {

    public static int length;
    private int id;
    private int student_id;
    private int book_isbn;
    private String date_borrowed;
    private String date_returned;
    private Map<String, Object> details;
    private Status status;

    public Transaction(int id, int student_id, int book_isbn, String date_borrowed, String date_returned) {
        if (uk.ac.leedsbeckett.Library.Portal.Controller.TransactionController.length == 1) {
            this.id = (int) Transaction[0].get("id");
            this.student_id = (int) Transaction[0].get("student_id");
            this.book_isbn = (int) Transaction[0].get("book_isbn");
            this.date_borrowed = (String) Transaction[0].get("date_borrowed");
            this.date_returned = (String) Transaction[0].get("date_returned");
        } else {
            this.id = (int) Transaction[0];
            this.student_id = (int) Transaction[1];
            this.book_isbn = (int) Transaction[2];
            this.date_borrowed = (String) Transaction[3];
            this.date_returned = (String) Transaction[4];
        }

        this.details = new HashMap<>();
        this.details.put("id", this.id);
        this.details.put("student_id", this.student_id);
        this.details.put("book_isbn", this.book_isbn);
        this.details.put("date_borrowed", this.date_borrowed);
        this.details.put("date_returned", this.date_returned);
    }

    public CollectionModel<EntityModel<Transaction>> getAllTransaction() {
    }

    public EntityModel<Transaction> getTransactionById(Long id) {
    }

    public EntityModel<Transaction> getTransactionByReference(String reference) {
    }
    public static EntityModel<Transaction> findTransactionByReference(String reference) {
    }

    public ResponseEntity<?> createNewTransaction(Transaction transaction) {
        ResponseEntity<?> o = null;
        return o;
    }

    public String getReference() {
        return null;
    }

    public Status getStatus() {
        return null;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}


