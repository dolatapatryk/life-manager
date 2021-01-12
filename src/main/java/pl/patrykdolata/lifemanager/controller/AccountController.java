package pl.patrykdolata.lifemanager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.patrykdolata.lifemanager.model.IdResponse;
import pl.patrykdolata.lifemanager.model.NewAccount;
import pl.patrykdolata.lifemanager.service.AccountService;
import pl.patrykdolata.lifemanager.util.ResponseUtils;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final Logger log = LoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<IdResponse> create(@RequestBody NewAccount account) {
        log.debug("Request to add new account: {}", account.getName());
        Long newAccountId = accountService.create(account);

        return ResponseUtils.response(new IdResponse(newAccountId), HttpStatus.CREATED);
    }
}
