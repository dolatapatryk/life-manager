package pl.patrykdolata.lifemanager.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.patrykdolata.lifemanager.model.JwtToken
import pl.patrykdolata.lifemanager.model.LoginInfo
import pl.patrykdolata.lifemanager.model.NewUser
import pl.patrykdolata.lifemanager.security.JwtFilter
import pl.patrykdolata.lifemanager.service.UserService
import pl.patrykdolata.lifemanager.util.ResponseUtils.ok

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    private val log: Logger = LoggerFactory.getLogger(UserController::class.java)

    @PostMapping("/authenticate", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun authorize(@RequestBody loginInfo: LoginInfo): ResponseEntity<JwtToken> {
        log.debug("Request to authorize user: {}", loginInfo.username)
        val token: JwtToken = userService.authorize(loginInfo)
        val headers = HttpHeaders()
        headers.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + token.token)

        return ok(token, headers)
    }

    @PostMapping("/register")
    fun register(@RequestBody user: NewUser): ResponseEntity<Void> {
        log.debug("Request to register new user: {}", user.username)
        userService.register(user)
        return ok()
    }
}
