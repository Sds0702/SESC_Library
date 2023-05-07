package uk.ac.leedsbeckett.Library.Portal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
@ControllerAdvice
public class TransactionNotValidAdvice
{
    @ResponseBody
    @ExceptionHandler(TransactionNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    String transactionNotValidHandler(TransactionNotValidException ex) { return ex.getMessage(); }
}
