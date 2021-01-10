package pl.patrykdolata.lifemanager.model;

import lombok.Value;

@Value
public class LoginInfo {
    String username;
    String password;
    Boolean rememberMe;
}
