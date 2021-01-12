package pl.patrykdolata.lifemanager.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.patrykdolata.lifemanager.domain.UserEntity;
import pl.patrykdolata.lifemanager.exceptions.EmailAlreadyExistsException;
import pl.patrykdolata.lifemanager.exceptions.PasswordsMatchException;
import pl.patrykdolata.lifemanager.exceptions.UsernameAlreadyExistsException;
import pl.patrykdolata.lifemanager.mapper.UserMapper;
import pl.patrykdolata.lifemanager.model.JwtToken;
import pl.patrykdolata.lifemanager.model.LoginInfo;
import pl.patrykdolata.lifemanager.model.NewUser;
import pl.patrykdolata.lifemanager.repository.UserRepository;
import pl.patrykdolata.lifemanager.security.AuthenticatedUser;
import pl.patrykdolata.lifemanager.security.JwtTokenProvider;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @SpyBean
    private JwtTokenProvider tokenProvider;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserMapper userMapper;
    @MockBean
    private AuthenticationManager authenticationManager;

    @Test
    public void register_whenPasswordsDontMatch_expectException() {
        NewUser newUser = createNewUser("pass1");
        assertThrows(PasswordsMatchException.class, () -> userService.register(newUser));
    }

    @Test
    public void register_whenEmailExists_expectException() {
        NewUser newUser = createNewUser();
        when(userRepository.findOneByEmail(newUser.getEmail()))
                .thenReturn(Optional.of(new UserEntity()));
        assertThrows(EmailAlreadyExistsException.class, () -> userService.register(newUser));
    }

    @Test
    public void register_whenUsernameExists_expectException() {
        NewUser newUser = createNewUser();
        when(userRepository.findOneByUsername(newUser.getUsername()))
                .thenReturn(Optional.of(new UserEntity()));
        assertThrows(UsernameAlreadyExistsException.class, () -> userService.register(newUser));
    }

    @Test
    public void register_whenProperNewUserData_expectSavedEntity() {
        NewUser newUser = createNewUser();
        when(userRepository.findOneByEmail(newUser.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findOneByUsername(newUser.getUsername())).thenReturn(Optional.empty());
        when(userMapper.toEntity(any(NewUser.class))).thenReturn(new UserEntity());

        userService.register(newUser);
        verify(userRepository).save(any(UserEntity.class));
    }

    private NewUser createNewUser() {
        return createNewUser("pass");
    }

    private NewUser createNewUser(String confirmPassword) {
        return new NewUser("username", "pass", confirmPassword, "test@test.pl", "John",
                "Doe");
    }

    @Test
    public void authorize_whenBadCredentials_expectException() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(BadCredentialsException.class);
        assertThrows(BadCredentialsException.class, () -> userService.authorize(createLoginInfo(true)));
    }

    @Test
    public void authorize_whenProperCredentialsNullRememberMe_expectProperToken() {
        authorize_whenProperCredentials_expectProperToken(null);
        verify(tokenProvider).createToken(any(Authentication.class), eq(false));
    }

    @Test
    public void authorize_whenProperCredentialsFalseRememberMe_expectProperToken() {
        authorize_whenProperCredentials_expectProperToken(false);
        verify(tokenProvider).createToken(any(Authentication.class), eq(false));
    }

    @Test
    public void authorize_whenProperCredentialsTrueRememberMe_expectProperToken() {
        authorize_whenProperCredentials_expectProperToken(true);
        verify(tokenProvider).createToken(any(Authentication.class), eq(true));
    }

    private void authorize_whenProperCredentials_expectProperToken(Boolean rememberMe) {
        LoginInfo loginInfo = createLoginInfo(rememberMe);
        AuthenticatedUser authUser = getAuthUser(loginInfo);
        UsernamePasswordAuthenticationToken auth = getAuth(authUser, loginInfo.getPassword());
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
        JwtToken result = userService.authorize(loginInfo);

        assertNotNull(result);
        assertThat(result.getToken(), is(not(emptyOrNullString())));
        assertSame(auth, SecurityContextHolder.getContext().getAuthentication());
    }

    private LoginInfo createLoginInfo(Boolean rememberMe) {
        return new LoginInfo("username", "pass", rememberMe);
    }

    private AuthenticatedUser getAuthUser(LoginInfo loginInfo) {
        return new AuthenticatedUser(1L, loginInfo.getUsername(), loginInfo.getPassword(),
                true, Collections.emptyList());
    }

    private UsernamePasswordAuthenticationToken getAuth(AuthenticatedUser authUser, String password) {
        return new UsernamePasswordAuthenticationToken(authUser, password);
    }
}
