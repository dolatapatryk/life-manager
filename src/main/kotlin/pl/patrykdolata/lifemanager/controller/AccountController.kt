package pl.patrykdolata.lifemanager.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.patrykdolata.lifemanager.model.IdResponse
import pl.patrykdolata.lifemanager.model.NewAccount
import pl.patrykdolata.lifemanager.service.AccountService
import pl.patrykdolata.lifemanager.util.ResponseUtils.response

@RestController
@RequestMapping("/api/accounts")
class AccountController(private val accountService: AccountService) {

    private val log: Logger = LoggerFactory.getLogger(AccountController::class.java)

    @PostMapping
    fun create(@RequestBody account: NewAccount): ResponseEntity<IdResponse> {
        log.debug("Request to add new account: {}", account.name)
        val newAccountId = accountService.create(account)

        return response(IdResponse(newAccountId), HttpStatus.CREATED)
    }
}
