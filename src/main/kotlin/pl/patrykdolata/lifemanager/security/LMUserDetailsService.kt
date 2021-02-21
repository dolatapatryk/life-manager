package pl.patrykdolata.lifemanager.security

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import pl.patrykdolata.lifemanager.domain.AuthorityEntity
import pl.patrykdolata.lifemanager.domain.UserEntity
import pl.patrykdolata.lifemanager.repository.UserRepository

@Component("userDetailsService")
class LMUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {

    private val log: Logger = LoggerFactory.getLogger(LMUserDetailsService::class.java)

    @Transactional(readOnly = true)
    override fun loadUserByUsername(username: String): UserDetails {
        log.debug("Authentication: $username")
        // TODO sprawdzic czy to email czy username
        val user: UserEntity? = userRepository.findOneByUsername(username)
        user?.let { return createAuthenticatedUser(it) } ?: throw UsernameNotFoundException("Username not found")
    }

    private fun createAuthenticatedUser(user: UserEntity): AuthenticatedUser {
        return AuthenticatedUser(user.id!!, user.username, user.password, user.email, user.firstName, user.lastName,
                user.activated, mapAuthorities(user.authorities))
    }

    private fun mapAuthorities(authorities: Set<AuthorityEntity>) =
            authorities.map { authority -> SimpleGrantedAuthority(authority.name) }
}
