package uk.ac.leedsbeckett.Library.Portal.exception;

public class BookNotValidException extends RuntimeException
{
  public BookNotValidException() { super(" Not a valid account."); }
  public BookNotValidException(String message) { super(message); }

}
