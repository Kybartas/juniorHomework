package org.kybartas.Account;

import org.kybartas.facade.BankFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;
    private final BankFacade bankFacade;
    public AccountController(AccountService accountService, BankFacade bankFacade) {
        this.accountService = accountService;
        this.bankFacade = bankFacade;
    }

    @GetMapping("/getBalance")
    public ResponseEntity<?> getBalance(
            @RequestParam("accountNumber") String accountNumber,
            @RequestParam(value = "from", required = false) LocalDate from,
            @RequestParam(value = "to", required = false) LocalDate to) {

        try{
            BigDecimal balance = bankFacade.getBalance(accountNumber, from, to);
            return ResponseEntity.ok(balance);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}
