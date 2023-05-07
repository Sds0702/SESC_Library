package uk.ac.leedsbeckett.Library.Portal.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import uk.ac.leedsbeckett.Library.Portal.Controller.TransactionController;
import uk.ac.leedsbeckett.Library.Portal.exception.TransactionNotValidException;

@Component
public class TransactionModelAssembler implements RepresentationModelAssembler<Transaction, EntityModel<Transaction>>
{
  @Override
  public EntityModel<Transaction> toModel(Transaction transaction)
  {
   if (!isValid(transaction))
   {
    throw new TransactionNotValidException();
   }
      EntityModel<Transaction> invoiceModel = EntityModel.of(transaction,
              linkTo(methodOn(TransactionController.class).one(transaction.getReference())).withSelfRel(),
              linkTo(methodOn(TransactionController.class).all()).withRel("transaction"));

      EntityModel<Transaction> transactionModel = null;
      if (transaction.getStatus() == Status.OUTSTANDING) {
         transactionModel.add(linkTo(methodOn(TransactionController.class).cancel(transaction.getReference())).withRel("cancel"));
      }

      return transactionModel;
   }
    private boolean isValid(Transaction transaction) {
        return transaction.getId() != null
                && transaction.getId() != 0
                && transaction.getReference() != null
                && !transaction.getReference().isEmpty()
                && transaction.getDetails() != null;
    }
}
