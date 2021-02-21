package pl.patrykdolata.lifemanager.service.impl

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import pl.patrykdolata.lifemanager.domain.UserEntity
import pl.patrykdolata.lifemanager.exceptions.EmailAlreadyExistsException
import pl.patrykdolata.lifemanager.exceptions.PasswordMatchException
import pl.patrykdolata.lifemanager.exceptions.UsernameAlreadyExistsException
import pl.patrykdolata.lifemanager.mapper.UserMapper
import pl.patrykdolata.lifemanager.model.LoginInfo
import pl.patrykdolata.lifemanager.model.NewUser
import pl.patrykdolata.lifemanager.model.User
import pl.patrykdolata.lifemanager.repository.UserRepository
import pl.patrykdolata.lifemanager.security.AuthenticatedUser
import pl.patrykdolata.lifemanager.security.JwtTokenProvider
import pl.patrykdolata.lifemanager.service.UserService

@Service
class UserServiceImpl(
        private val userRepository: UserRepository, private val userMapper: UserMapper,
        private val authenticationManager: AuthenticationManager, private val tokenProvider: JwtTokenProvider
) : UserService {

    private val log: Logger = LoggerFactory.getLogger(UserServiceImpl::class.java)

    override fun authorize(loginInfo: LoginInfo): User {
        log.debug("Authorize user: ${loginInfo.username}")
        val token = UsernamePasswordAuthenticationToken(loginInfo.username, loginInfo.password)
        val authentication: Authentication = authenticationManager.authenticate(token)
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = tokenProvider.createToken(authentication, loginInfo.rememberMe)
        val user: AuthenticatedUser = authentication.principal as AuthenticatedUser

        return User(user, jwt)
    }

    override fun register(user: NewUser) {
        log.debug("Register new user with username: ${user.username}")
        validNewUser(user)
        val entity = userMapper.toEntity(user)
        userRepository.save(entity)
    }

    private fun validNewUser(user: NewUser) {
        if (!checkPasswordsMatch(user.password, user.confirmPassword)) {
            throw PasswordMatchException()
        }
        if (checkUserWithEmailExists(user.email)) {
            throw EmailAlreadyExistsException()
        }
        if (checkUserWithUsernameExists(user.username)) {
            throw UsernameAlreadyExistsException()
        }
    }

    private fun checkPasswordsMatch(password: String, confirmPassword: String): Boolean = password == confirmPassword

    private fun checkUserWithEmailExists(email: String): Boolean {
        val user: UserEntity? = userRepository.findOneByEmail(email)
        return user != null
    }

    private fun checkUserWithUsernameExists(username: String): Boolean {
        val user: UserEntity? = userRepository.findOneByUsername(username)
        return user != null
    }
}
