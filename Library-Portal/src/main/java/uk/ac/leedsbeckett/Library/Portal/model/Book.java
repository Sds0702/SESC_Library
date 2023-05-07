package uk.ac.leedsbeckett.Library.Portal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import uk.ac.leedsbeckett.Library.Portal.Controller.BookController;

import javax.persistence.*;
import javax.transaction.Transaction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Data
public class Book
{
    private int bookIsbn;
    private int title;
    private int author;
    private int year;
    private int copies;

   public Book(int bookIsbn, int title, int author, int year, int copies) {
       if (BookController.length == 1) {
           this.bookIsbn = (int) Book[0].get("bookIsbn");
           this.title = (int) Book[0].get("title");
           this.author = (int) Book[0].get("author");
           this.year = (int) Book[0].get("year");
           this.copies = (int) Book[0].get("copies");
       } else {
           this.bookIsbn = (int) Book[0];
           this.title = (int) Book[1];
           this.author = (int) Book[2];
           this.year = (int) Book[3];
           this.copies = (int) Book[4];
       }
     }
   }
   /* public static String getStudent_id;
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long isbn;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    private List<Transaction> transactionList = new ArrayList<>();
    @Transient
    private boolean hasOutstandingBalance;
    private boolean populateOutstandingBalance;
    private String Id;

    public Book(String bookIsbn)
    {
        this.bookIsbn = bookIsbn;
    }

   public String getStudentId() {
        return Id;
    }*/

