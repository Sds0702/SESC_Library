package uk.ac.leedsbeckett.Library.Portal.exception;

public class TransactionNotValidException extends RuntimeException
{
    public TransactionNotValidException() {
        super("Not a valid transaction.");
    }
    public TransactionNotValidException(String message) {
        super(message);
    }
}
