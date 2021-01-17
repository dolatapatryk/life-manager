package pl.patrykdolata.lifemanager.security

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import pl.patrykdolata.lifemanager.domain.UserEntity
import pl.patrykdolata.lifemanager.exceptions.NotLoggedUserException

object AuthenticationUtils {

    fun getCurrentUserId(): Long {
        val authentication: Authentication? = SecurityContextHolder.getContext().authentication
        if (authentication?.principal is AuthenticatedUser) {
            return (authentication.principal as AuthenticatedUser).id
        }
        throw NotLoggedUserException()
    }

    @JvmStatic
    fun getCurrentUser(): UserEntity {
        val id = getCurrentUserId()
        val user = UserEntity()
        user.id = id

        return user
    }
}
