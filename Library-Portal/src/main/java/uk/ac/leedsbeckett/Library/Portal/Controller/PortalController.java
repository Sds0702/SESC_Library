package uk.ac.leedsbeckett.Library.Portal.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import uk.ac.leedsbeckett.Library.Portal.model.Transaction;
import uk.ac.leedsbeckett.Library.Portal.service.TransactionService;

import javax.validation.Valid;

@Controller
public class PortalController
{
  private final TransactionService transactionService;
  public PortalController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @GetMapping({ "/"})
  public String redirectToHome(Model model) {
    return "redirect:/portal";
  }

  @GetMapping({ "/portal", "/portal/transaction"})
  public String showPortal(Model model) {
    return transactionService.showPortal(model);
  }

  @PostMapping("/portal/transaction")
  public String findTransaction( @ModelAttribute @Valid Transaction transaction, BindingResult bindingResult, Model model) {
    return transactionService.findTransactionThroughPortal(transaction, bindingResult, model);
  }

  @PostMapping("/portal/pay")
  public String payTransaction(@ModelAttribute Transaction transaction, Model model) {
    return transactionService.payTransactionThroughPortal(transaction, model);
  }
}
