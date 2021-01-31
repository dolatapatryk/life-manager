package pl.patrykdolata.lifemanager.mapper

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.function.Executable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import pl.patrykdolata.lifemanager.model.NewUser
import java.util.*

@SpringBootTest
class UserMapperTest {

    @Autowired
    private lateinit var mapper: UserMapper

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Test
    fun `Mapping empty password should return random encrypt password`() {
        val password = ""
        val result = mapper.mapPassword(password)
        assertPasswordLength(result)
    }

    @Test
    fun `Mapping null password should throw NPE`() {
        val password: String? = null
        assertThrows<NullPointerException> { mapper.mapPassword(password!!) }
    }

    @Test
    fun `Mapping proper password should return specific encrypt password string`() {
        val password = "test_password"
        val result = mapper.mapPassword(password)
        assertPasswordLength(result)
        assertTrue(passwordEncoder.matches(password, result))
    }

    private fun assertPasswordLength(result: String) {
        assertNotNull(result)
        assertThat(result, hasLength(60))
    }

    @Test
    fun `Mapping null NewUser should throw NPE`() {
        val user: NewUser? = null
        assertThrows<NullPointerException> { mapper.toEntity(user!!) }
    }

    @Test
    fun `Mapping proper NewUser should return proper UserEntity`() {
        val user = NewUser("username", "pass", "pass", "test@test.com",
                "John", "Doe")
        val result = mapper.toEntity(user)
        assertAll(
                Executable { assertNotNull(result) },
                Executable { assertThat(result.username, equalTo("username")) },
                Executable { assertThat(result.firstName, equalTo("John")) },
                Executable { assertThat(result.lastName, equalTo("Doe")) },
                Executable { assertThat(result.email, equalTo("test@test.com")) },
                Executable { assertTrue(result.activated) },
                Executable { assertThat(result.activationKey, hasLength(UserMapper.KEY_LENGTH)) },
                Executable { assertThat(result.createdAt.toDouble(), closeTo(Date().time.toDouble(), 60000.0)) }
        )
    }
}
