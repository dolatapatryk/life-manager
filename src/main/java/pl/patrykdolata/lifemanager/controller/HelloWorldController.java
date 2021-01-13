package pl.patrykdolata.lifemanager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.patrykdolata.lifemanager.security.AuthenticatedUser;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class HelloWorldController {

    private final Logger log = LoggerFactory.getLogger(HelloWorldController.class);

    @GetMapping("/hello")
    public String helloWorld() {
        log.debug("Hello world endpoint");
        return "hello world";
    }

    @GetMapping("/name")
    public String getName(Authentication authentication, Principal principal) {
        AuthenticatedUser other = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.debug("userId: {}", other.getId());
        log.debug("username: {}", other.getUsername());

        return "success";
    }
}
