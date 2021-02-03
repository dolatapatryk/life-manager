package pl.patrykdolata.lifemanager.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.patrykdolata.lifemanager.domain.AccountEntity
import pl.patrykdolata.lifemanager.model.Account
import pl.patrykdolata.lifemanager.service.AccountService

@RestController
@RequestMapping("/api/accounts")
class AccountController(accountService: AccountService)
    : AbstractCrudController<Account, AccountEntity, Long>(accountService) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(AccountController::class.java)
    }

    override fun getLogger(): Logger = logger

    override fun getModelClassName(): String? = Account::class.simpleName
}
