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
import pl.patrykdolata.lifemanager.service.UserService;
import pl.patrykdolata.lifemanager.util.ResponseUtils;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JwtToken> authorize(@RequestBody LoginInfo loginInfo) {
        log.debug("Request to authorize user: {}", loginInfo.getUsername());
        JwtToken token = userService.authorize(loginInfo);
        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + token.getToken());

        return ResponseUtils.ok(token, headers);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody NewUser user) {
        log.debug("Request to register new user: {}", user.getUsername());
        userService.register(user);

        return ResponseUtils.ok();
    }
}
