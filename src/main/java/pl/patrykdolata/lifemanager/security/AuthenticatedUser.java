package pl.patrykdolata.lifemanager.security;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Value
@EqualsAndHashCode(callSuper = true)
public class AuthenticatedUser extends User {

    Long id;

    public AuthenticatedUser(Long id, String username, String password, boolean activated,
                             Collection<GrantedAuthority> authorities) {
        super(username, password, activated, true, true, true,
                authorities);
        this.id = id;
    }
}
