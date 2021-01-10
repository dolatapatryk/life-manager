package pl.patrykdolata.lifemanager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloWorldController {

    private final Logger log = LoggerFactory.getLogger(HelloWorldController.class);

    @GetMapping("/hello")
    public String helloWorld() {
        log.debug("Hello world endpoint");
        return "hello world";
    }
}
