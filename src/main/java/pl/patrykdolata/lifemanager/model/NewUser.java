package pl.patrykdolata.lifemanager.model;

import lombok.Value;

@Value
public class NewUser {
    String username;
    String password;
    String confirmPassword;
    String email;
    String firstName;
    String lastName;
}
