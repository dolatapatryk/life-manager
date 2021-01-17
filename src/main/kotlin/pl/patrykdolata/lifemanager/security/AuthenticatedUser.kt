package pl.patrykdolata.lifemanager.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class AuthenticatedUser(
        val id: Long, username: String, password: String, activated: Boolean,
        authorities: Collection<GrantedAuthority>
) : User(username, password, activated, true, true, true, authorities)
