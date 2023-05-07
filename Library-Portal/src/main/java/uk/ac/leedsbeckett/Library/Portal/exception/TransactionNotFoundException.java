package uk.ac.leedsbeckett.Library.Portal.exception;

public class TransactionNotFoundException extends RuntimeException
{
    public TransactionNotFoundException(Long id)
    {
        super("Could not find transaction " + id);
    }

    public TransactionNotFoundException(String reference)
    {
        super("Could not find transaction for reference " + reference);
    }

    public TransactionNotFoundException()
    {
        super("Could not find transaction.");
    }

}
