package pl.patrykdolata.lifemanager

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import pl.patrykdolata.lifemanager.security.AuthenticatedUser

object TestUtils {
    const val ID = 3L

    fun withProperPrincipal() {
        val principal = AuthenticatedUser(ID, "user", "pass", true, emptyList())
        SecurityContextHolder.getContext().authentication = getAuthentication(principal)
    }

    fun withStringPrincipal() {
        SecurityContextHolder.getContext().authentication = getAuthentication("user")
    }

    fun withNullAuthentication() {
        SecurityContextHolder.getContext().authentication = null
    }

    private fun getAuthentication(principal: Any): Authentication {
        return UsernamePasswordAuthenticationToken(principal, "pass")
    }
}
