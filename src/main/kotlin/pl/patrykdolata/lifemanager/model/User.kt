package pl.patrykdolata.lifemanager.model

import pl.patrykdolata.lifemanager.security.AuthenticatedUser

data class User(val id: Long, val username: String, val email: String, val firstName: String, val lastName: String,
                val token: String, val authorities: List<String> = emptyList()) {

    constructor(user: AuthenticatedUser, jwt: String)
            : this(user.id, user.username, user.email, user.firstName, user.lastName, jwt,
            user.authorities.map { authority -> authority.authority })
}
