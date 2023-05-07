package uk.ac.leedsbeckett.Library.Portal.model;

import uk.ac.leedsbeckett.Library.Portal.constants.constants;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Fine {
    private int days_borrowed;
    private double amount;
    private String due;
    private String type;
    private String student_id;
    private String details;

    public Fine(LocalDate borrowed, int student_id) throws ValueError {
        this.days_borrowed = days_before(borrowed, LocalDate.now());
        this.amount = this.calculate_fine(borrowed);
        this.due = LocalDate.now().plus(constants.MAX_PAYMENT_DURATION, ChronoUnit.DAYS).toString();
        this.type = "LIBRARY_FINE";
        this.student_id = "studentId" + Integer.toString(student_id);
        this.details = "amount: " + Double.toString(this.amount) + ", dueDate: " + this.due + ", type: " + this.type + ", account: " + this.student_id;
    }

    private int days_before(LocalDate borrowed, LocalDate now) {
        return 0;
    }

    public double calculate_fine(LocalDate borrowed) throws ValueError {
        long days_late = days_before(borrowed.plus(constants.MAX_BORROWING_DURATION, ChronoUnit.DAYS), LocalDate.now());
        if (days_late >= 1) {
            return Objects.requireNonNull(constants.FINE_PER_DAY * days_late);
        }
        else throw new ValueError("No fine necessary.");
    }

    public class ValueError extends Throwable {
        public ValueError(String s) {
        }
    }
}
