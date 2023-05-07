package uk.ac.leedsbeckett.Library.Portal.exception;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException()
    {
        super("Could not find account " + id);
    }
    public BookNotFoundException(String studentId)
    {
        super("Could not find account for student ID " + studentId);
    }
}
