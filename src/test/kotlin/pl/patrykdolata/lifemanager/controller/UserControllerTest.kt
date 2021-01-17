package pl.patrykdolata.lifemanager.controller

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.whenever
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import pl.patrykdolata.lifemanager.exceptions.EmailAlreadyExistsException
import pl.patrykdolata.lifemanager.exceptions.PasswordMatchException
import pl.patrykdolata.lifemanager.exceptions.UsernameAlreadyExistsException
import pl.patrykdolata.lifemanager.model.JwtToken
import pl.patrykdolata.lifemanager.model.LoginInfo
import pl.patrykdolata.lifemanager.model.NewUser
import pl.patrykdolata.lifemanager.security.JwtFilter
import pl.patrykdolata.lifemanager.service.UserService
import pl.patrykdolata.lifemanager.util.JsonUtils.asJsonString

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    companion object {
        private const val URL = "/api/users"
        private const val URL_REGISTER = "$URL/register"
        private const val URL_AUTHENTICATE = "$URL/authenticate"
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var userService: UserService

    private val newUser: NewUser = NewUser("user", "pass", "pass",
            "test@test.com", "John", "Doe")
    private val loginInfo: LoginInfo = LoginInfo("user", "pass", false)

    @Test
    @Throws(Exception::class)
    fun `Register with passwords don't match should return status 422`() {
        doThrow(PasswordMatchException()).whenever(userService).register(any())
        mockMvc.perform(post(URL_REGISTER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(newUser)!!))
                .andExpect(status().isUnprocessableEntity)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", equalTo(PasswordMatchException.MESSAGE)))
                .andExpect(jsonPath("$.status", equalTo(HttpStatus.UNPROCESSABLE_ENTITY.value())))
                .andExpect(jsonPath("$.path", equalTo(URL_REGISTER)))
    }

    @Test
    @Throws(Exception::class)
    fun `Register with existing email should return status 409`() {
        doThrow(EmailAlreadyExistsException()).`when`(userService).register(any())
        mockMvc.perform(post(URL_REGISTER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(newUser)!!))
                .andExpect(status().isConflict)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", equalTo(EmailAlreadyExistsException.MESSAGE)))
                .andExpect(jsonPath("$.status", equalTo(HttpStatus.CONFLICT.value())))
                .andExpect(jsonPath("$.path", equalTo(URL_REGISTER)))
    }

    @Test
    @Throws(Exception::class)
    fun `Register with existing username should return status 409`() {
        doThrow(UsernameAlreadyExistsException()).`when`(userService).register(any())
        mockMvc.perform(post(URL_REGISTER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(newUser)!!))
                .andExpect(status().isConflict)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", equalTo(UsernameAlreadyExistsException.MESSAGE)))
                .andExpect(jsonPath("$.status", equalTo(HttpStatus.CONFLICT.value())))
                .andExpect(jsonPath("$.path", equalTo(URL_REGISTER)))
    }

    @Test
    @Throws(Exception::class)
    fun `Register with proper data should return status 200`() {
        mockMvc.perform(post(URL_REGISTER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(newUser)!!))
                .andExpect(status().isOk)
    }

    @Test
    @Disabled
    @Throws(Exception::class)
    fun `Authorize with bad credentials should return 401`() {
        whenever(userService.authorize(any())).thenThrow(BadCredentialsException("Bad credentials"))
        mockMvc.perform(post(URL_AUTHENTICATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(loginInfo)!!))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isUnauthorized)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", equalTo("Bad credentials")))
                .andExpect(jsonPath("$.status", equalTo(HttpStatus.UNAUTHORIZED.value())))
                .andExpect(jsonPath("$.path", equalTo(URL_AUTHENTICATE)))
    }

    @Test
    @Throws(Exception::class)
    fun `Authorize with proper credentials should return status 200 and token`() {
        whenever(userService.authorize(any())).thenReturn(JwtToken("some_token"))
        mockMvc.perform(post(URL_AUTHENTICATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(loginInfo)!!))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().exists(JwtFilter.AUTHORIZATION_HEADER))
                .andExpect(header().string(JwtFilter.AUTHORIZATION_HEADER, "Bearer some_token"))
                .andExpect(jsonPath("$.token", not(emptyString())))
    }
}
