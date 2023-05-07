package uk.ac.leedsbeckett.Library.Portal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
@ControllerAdvice
public class TransactionNotFoundAdvice
{
 @ResponseBody
 @ExceptionHandler(TransactionNotFoundException.class)
 @ResponseStatus(HttpStatus.NOT_FOUND)
 String transactionNotFoundHandler(TransactionNotFoundException ex) { return ex.getMessage(); }
}
