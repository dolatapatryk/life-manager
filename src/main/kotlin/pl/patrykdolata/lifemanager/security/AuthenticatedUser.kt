package pl.patrykdolata.lifemanager.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class AuthenticatedUser(
        val id: Long, username: String, password: String = "", val email: String = "", val firstName: String = "",
        val lastName: String = "", activated: Boolean = true, authorities: Collection<GrantedAuthority> = emptySet()
) : User(username, password, activated, true, true, true, authorities)
