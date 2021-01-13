package pl.patrykdolata.lifemanager.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.patrykdolata.lifemanager.domain.UserEntity;
import pl.patrykdolata.lifemanager.model.NewUser;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void mapPassword_whenEmptyPassword_expectRandomString() {
        String password = "";
        String result = mapper.mapPassword(password);

        assertPasswordLength(result);
    }

    @Test
    public void mapPassword_whenNullPassword_expectRandomString() {
        String password = null;
        String result = mapper.mapPassword(password);

        assertPasswordLength(result);
    }

    @Test
    public void mapPassword_whenSpecificPassword_expectProperString() {
        String password = "test_password";
        String result = mapper.mapPassword(password);

        assertPasswordLength(result);
        assertTrue(passwordEncoder.matches(password, result));
    }

    private void assertPasswordLength(String result) {
        assertNotNull(result);
        assertThat(result, hasLength(60));
    }

    @Test
    public void toEntity_whenNullModel_expectNull() {
        NewUser user = null;
        UserEntity result = mapper.toEntity(user);

        assertNull(result);
    }

    @Test
    public void toEntity_whenProperModel_expectProperEntity() {
        NewUser user = new NewUser("username", "pass", "pass", "test@test.com",
                "John", "Doe");
        UserEntity result = mapper.toEntity(user);

        assertAll(
                () -> assertNotNull(result),
                () -> assertThat(result.getUsername(), is("username")),
                () -> assertThat(result.getFirstName(), is("John")),
                () -> assertThat(result.getLastName(), is("Doe")),
                () -> assertThat(result.getEmail(), is("test@test.com")),
                () -> assertFalse(result.getActivated()),
                () -> assertThat(result.getActivationKey(), hasLength(UserMapper.KEY_LENGTH)),
                () -> assertThat(result.getCreatedAt().doubleValue(), closeTo(new Date().getTime(), 60000.0))
        );
    }
}
