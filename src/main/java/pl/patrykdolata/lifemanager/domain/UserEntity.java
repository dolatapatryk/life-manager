package pl.patrykdolata.lifemanager.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "username", nullable = false, length = 50, unique = true)
    private String username;

    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password", nullable = false, length = 60)
    private String password;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Email
    @NotNull
    @Size(min = 5, max = 254)
    @Column(name = "email", nullable = false, length = 254, unique = true)
    private String email;

    @NotNull
    @Column(name = "activated", nullable = false)
    private Boolean activated;

    @Size(max = 30)
    @Column(name = "activation_key", length = 30)
    private String activationKey;

    @Size(max = 30)
    @Column(name = "reset_key", length = 30)
    private String resetKey;

    @Column(name = "created_at")
    private Long createdAt;
}
