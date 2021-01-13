package pl.patrykdolata.lifemanager.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import pl.patrykdolata.lifemanager.domain.UserEntity;
import pl.patrykdolata.lifemanager.exceptions.NotLoggedUserException;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AuthenticationUtilsTest {

    private static final Long ID = 3L;

    @WithAnonymousUser
    @Test
    void getCurrentUserId_whenNullAuthentication_expectException() {
        assertThrows(NotLoggedUserException.class, AuthenticationUtils::getCurrentUserId);
    }

    @Test
    void getCurrentUserId_whenStringPrincipal_expectException() {
        SecurityContextHolder.getContext().setAuthentication(getAuthentication("user"));
        assertThrows(NotLoggedUserException.class, AuthenticationUtils::getCurrentUserId);
    }

    @Test
    void getCurrentUserId_whenProperPrincipal_expectId() {
        withProperPrincipal();
        Long result = AuthenticationUtils.getCurrentUserId();
        assertThat(result, is(ID));
    }

    @Test
    void getCurrentUser_whenProperPrincipal_expectUser() {
        withProperPrincipal();
        UserEntity result = AuthenticationUtils.getCurrentUser();
        assertNotNull(result);
        assertThat(result.getId(), is(ID));
    }

    private void withProperPrincipal() {
        AuthenticatedUser principal = new AuthenticatedUser(ID, "user", "pass", true,
                Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(principal));
    }

    private Authentication getAuthentication(Object principal) {
        return new UsernamePasswordAuthenticationToken(principal, "pass");
    }
}
