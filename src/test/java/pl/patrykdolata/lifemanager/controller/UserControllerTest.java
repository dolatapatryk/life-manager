package pl.patrykdolata.lifemanager.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;
import pl.patrykdolata.lifemanager.exceptions.EmailAlreadyExistsException;
import pl.patrykdolata.lifemanager.exceptions.PasswordsMatchException;
import pl.patrykdolata.lifemanager.exceptions.UsernameAlreadyExistsException;
import pl.patrykdolata.lifemanager.model.JwtToken;
import pl.patrykdolata.lifemanager.model.LoginInfo;
import pl.patrykdolata.lifemanager.model.NewUser;
import pl.patrykdolata.lifemanager.security.JwtFilter;
import pl.patrykdolata.lifemanager.service.UserService;
import pl.patrykdolata.lifemanager.util.JsonUtils;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    private static final String URL = "/api/users";
    private static final String URL_REGISTER = URL + "/register";
    private static final String URL_AUTHENTICATE = URL + "/authenticate";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private final NewUser newUser = new NewUser("user", "pass", "pass",
            "test@test.com", "John", "Doe");
    private final LoginInfo loginInfo = new LoginInfo("user", "pass", false);

    @Test
    public void register_whenPasswordsDontMatch_expect422() throws Exception {
        doThrow(new PasswordsMatchException()).when(userService).register(any(NewUser.class));

        mockMvc.perform(post(URL_REGISTER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.asJsonString(newUser)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is(PasswordsMatchException.MESSAGE)))
                .andExpect(jsonPath("$.status", is(HttpStatus.UNPROCESSABLE_ENTITY.value())))
                .andExpect(jsonPath("$.path", is(URL_REGISTER)));
    }

    @Test
    public void register_whenEmailExists_expect409() throws Exception {
        doThrow(new EmailAlreadyExistsException()).when(userService).register(any(NewUser.class));

        mockMvc.perform(post(URL_REGISTER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.asJsonString(newUser)))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is(EmailAlreadyExistsException.MESSAGE)))
                .andExpect(jsonPath("$.status", is(HttpStatus.CONFLICT.value())))
                .andExpect(jsonPath("$.path", is(URL_REGISTER)));
    }

    @Test
    public void register_whenUsernameExists_expect409() throws Exception {
        doThrow(new UsernameAlreadyExistsException()).when(userService).register(any(NewUser.class));

        mockMvc.perform(post(URL_REGISTER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.asJsonString(newUser)))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is(UsernameAlreadyExistsException.MESSAGE)))
                .andExpect(jsonPath("$.status", is(HttpStatus.CONFLICT.value())))
                .andExpect(jsonPath("$.path", is(URL_REGISTER)));
    }

    @Test
    public void register_whenEverythingIsOk_expect200() throws Exception {
        mockMvc.perform(post(URL_REGISTER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.asJsonString(newUser)))
                .andExpect(status().isOk());
    }

    @Test
    public void authorize_whenAuthenticationException_expect401() throws Exception {
        when(userService.authorize(any(LoginInfo.class))).thenThrow(new BadCredentialsException("Bad credentials"));

        mockMvc.perform(post(URL_AUTHENTICATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.asJsonString(loginInfo)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Bad credentials")))
                .andExpect(jsonPath("$.status", is(HttpStatus.UNAUTHORIZED.value())))
                .andExpect(jsonPath("$.path", is(URL_AUTHENTICATE)));
    }

    @Test
    public void authorize_whenEverythingIsOk_expect200() throws Exception {
        when(userService.authorize(any(LoginInfo.class))).thenReturn(new JwtToken("some_token"));
        mockMvc.perform(post(URL_AUTHENTICATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.asJsonString(loginInfo)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().exists(JwtFilter.AUTHORIZATION_HEADER))
                .andExpect(header().string(JwtFilter.AUTHORIZATION_HEADER, "Bearer some_token"))
                .andExpect(jsonPath("$.token", is(not(emptyString()))));
    }
}
