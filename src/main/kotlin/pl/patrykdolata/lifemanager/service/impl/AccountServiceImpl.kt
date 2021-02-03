package pl.patrykdolata.lifemanager.service.impl

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import pl.patrykdolata.lifemanager.domain.AccountEntity
import pl.patrykdolata.lifemanager.mapper.AccountMapper
import pl.patrykdolata.lifemanager.model.Account
import pl.patrykdolata.lifemanager.repository.AccountRepository
import pl.patrykdolata.lifemanager.service.AccountService

@Service
class AccountServiceImpl(accountRepository: AccountRepository, accountMapper: AccountMapper)
    : CrudServiceImpl<Account, AccountEntity, Long>(accountRepository, accountMapper), AccountService {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(AccountServiceImpl::class.java)
    }

    override fun getLogger(): Logger = logger

    override fun getModelClassName(): String? = Account::class.simpleName
}
