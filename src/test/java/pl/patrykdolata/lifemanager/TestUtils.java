package pl.patrykdolata.lifemanager;

import lombok.experimental.UtilityClass;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.patrykdolata.lifemanager.security.AuthenticatedUser;

import java.util.Collections;

@UtilityClass
public class TestUtils {

    public Long ID = 3L;

    public void withProperPrincipal() {
        AuthenticatedUser principal = new AuthenticatedUser(ID, "user", "pass", true,
                Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(principal));
    }

    public void withStringPrincipal() {
        SecurityContextHolder.getContext().setAuthentication(getAuthentication("user"));

    }

    private Authentication getAuthentication(Object principal) {
        return new UsernamePasswordAuthenticationToken(principal, "pass");
    }
}
