package pl.patrykdolata.lifemanager.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithAnonymousUser;
import pl.patrykdolata.lifemanager.TestUtils;
import pl.patrykdolata.lifemanager.domain.UserEntity;
import pl.patrykdolata.lifemanager.exceptions.NotLoggedUserException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AuthenticationUtilsTest {

    @WithAnonymousUser
    @Test
    void getCurrentUserId_whenNullAuthentication_expectException() {
        assertThrows(NotLoggedUserException.class, AuthenticationUtils::getCurrentUserId);
    }

    @Test
    void getCurrentUserId_whenStringPrincipal_expectException() {
        TestUtils.withStringPrincipal();
        assertThrows(NotLoggedUserException.class, AuthenticationUtils::getCurrentUserId);
    }

    @Test
    void getCurrentUserId_whenProperPrincipal_expectId() {
        TestUtils.withProperPrincipal();
        Long result = AuthenticationUtils.getCurrentUserId();
        assertThat(result, is(TestUtils.ID));
    }

    @Test
    void getCurrentUser_whenProperPrincipal_expectUser() {
        TestUtils.withProperPrincipal();
        UserEntity result = AuthenticationUtils.getCurrentUser();
        assertNotNull(result);
        assertThat(result.getId(), is(TestUtils.ID));
    }
}
