package pl.patrykdolata.lifemanager.service

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.emptyOrNullString
import org.hamcrest.Matchers.not
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.eq
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import pl.patrykdolata.lifemanager.domain.UserEntity
import pl.patrykdolata.lifemanager.exceptions.EmailAlreadyExistsException
import pl.patrykdolata.lifemanager.exceptions.PasswordMatchException
import pl.patrykdolata.lifemanager.exceptions.UsernameAlreadyExistsException
import pl.patrykdolata.lifemanager.mapper.UserMapper
import pl.patrykdolata.lifemanager.model.LoginInfo
import pl.patrykdolata.lifemanager.model.NewUser
import pl.patrykdolata.lifemanager.repository.UserRepository
import pl.patrykdolata.lifemanager.security.AuthenticatedUser
import pl.patrykdolata.lifemanager.security.JwtTokenProvider

@SpringBootTest
class UserServiceTest {

    @Autowired
    private lateinit var userService: UserService

    @SpyBean
    private lateinit var tokenProvider: JwtTokenProvider

    @MockBean
    private lateinit var userRepository: UserRepository

    @MockBean
    private lateinit var userMapper: UserMapper

    @MockBean
    private lateinit var authenticationManager: AuthenticationManager

    @Test
    fun `Register user when passwords don't match should throw exception`() {
        val newUser = createNewUser("pass1")
        assertThrows<PasswordMatchException> { userService.register(newUser) }
    }

    @Test
    fun `Register user when email exists should throw exception`() {
        val newUser = createNewUser()
        whenever(userRepository.findOneByEmail(newUser.email)).thenReturn(UserEntity())
        assertThrows<EmailAlreadyExistsException> { userService.register(newUser) }
    }

    @Test
    fun `Register user when username exists should throw exception`() {
        val newUser = createNewUser()
        whenever(userRepository.findOneByUsername(newUser.username)).thenReturn(UserEntity())
        assertThrows<UsernameAlreadyExistsException> { userService.register(newUser) }
    }

    @Test
    fun `Register user with proper NewUser should return new saved UserEntity`() {
        val newUser = createNewUser()
        whenever(userRepository.findOneByEmail(newUser.email)).thenReturn(null)
        whenever(userRepository.findOneByUsername(newUser.username)).thenReturn(null)
        whenever(userMapper.toEntity(any())).thenReturn(UserEntity())
        userService.register(newUser)
        verify(userRepository).save(any())
    }

    private fun createNewUser(): NewUser {
        return createNewUser("pass")
    }

    private fun createNewUser(confirmPassword: String): NewUser {
        return NewUser("username", "pass", confirmPassword, "test@test.pl", "John",
                "Doe")
    }

    @Test
    fun `Authorize with bad credentials should throw exception`() {
        whenever(authenticationManager.authenticate(any()))
                .thenThrow(BadCredentialsException::class.java)
        val loginInfo = createLoginInfo(true)
        assertThrows<BadCredentialsException> { userService.authorize(loginInfo) }
    }

    @Test
    fun `Authorize with proper credentials and false remember me should return proper token`() {
        authorize_whenProperCredentials_expectProperToken(false)
        verify(tokenProvider).createToken(any(), eq(false))
    }

    @Test
    fun `Authorize with proper credentials and true remember me should return proper token`() {
        authorize_whenProperCredentials_expectProperToken(true)
        verify(tokenProvider).createToken(any(), eq(true))
    }

    private fun authorize_whenProperCredentials_expectProperToken(rememberMe: Boolean) {
        val loginInfo = createLoginInfo(rememberMe)
        val authUser = getAuthUser(loginInfo)
        val auth = getAuth(authUser, loginInfo.password)
        whenever(authenticationManager.authenticate(any())).thenReturn(auth)
        val result = userService.authorize(loginInfo)
        assertNotNull(result)
        assertThat(result.token, not(emptyOrNullString()))
        assertSame(auth, SecurityContextHolder.getContext().authentication)
    }

    private fun createLoginInfo(rememberMe: Boolean): LoginInfo {
        return LoginInfo("username", "pass", rememberMe)
    }

    private fun getAuthUser(loginInfo: LoginInfo): AuthenticatedUser {
        return AuthenticatedUser(1L, loginInfo.username, loginInfo.password,
                true, emptyList())
    }

    private fun getAuth(authUser: AuthenticatedUser, password: String): UsernamePasswordAuthenticationToken {
        return UsernamePasswordAuthenticationToken(authUser, password)
    }
}
