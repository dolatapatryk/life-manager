package pl.patrykdolata.lifemanager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.patrykdolata.lifemanager.model.JwtToken;
import pl.patrykdolata.lifemanager.model.LoginInfo;
import pl.patrykdolata.lifemanager.model.NewUser;
import pl.patrykdolata.lifemanager.security.JwtFilter;
import pl.patrykdolata.lifemanager.service.AccountService;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final Logger log = LoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JwtToken> authorize(@RequestBody LoginInfo loginInfo) {
        log.debug("Request to authorize user: {}", loginInfo.getUsername());
        JwtToken token = accountService.authorize(loginInfo);
        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + token.getToken());

        return new ResponseEntity<>(token, headers, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody NewUser user) {
        log.debug("Request to register new user: {}", user.getUsername());
        accountService.register(user);

        return ResponseEntity.ok().build();
    }
}
