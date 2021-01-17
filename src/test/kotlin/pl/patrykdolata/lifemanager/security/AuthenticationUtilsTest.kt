package pl.patrykdolata.lifemanager.security

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import pl.patrykdolata.lifemanager.TestUtils
import pl.patrykdolata.lifemanager.exceptions.NotLoggedUserException
import pl.patrykdolata.lifemanager.security.AuthenticationUtils.getCurrentUser
import pl.patrykdolata.lifemanager.security.AuthenticationUtils.getCurrentUserId

class AuthenticationUtilsTest {

    @Test
    fun `Given null authentication should throw exception`() {
        TestUtils.withNullAuthentication()
        assertThrows(NotLoggedUserException::class.java) { getCurrentUserId() }
    }

    @Test
    fun `Given string principal should throw exception`() {
        TestUtils.withStringPrincipal()
        assertThrows(NotLoggedUserException::class.java) { getCurrentUserId() }
    }

    @Test
    fun `Given proper principal should return proper user id`() {
        TestUtils.withProperPrincipal()
        val result = getCurrentUserId()
        assertThat(result, equalTo(TestUtils.ID))
    }

    @Test
    fun `Given proper principal should return proper user entity`() {
        TestUtils.withProperPrincipal()
        val result = getCurrentUser()
        assertNotNull(result)
        assertThat(result.id, equalTo(TestUtils.ID))
    }
}
