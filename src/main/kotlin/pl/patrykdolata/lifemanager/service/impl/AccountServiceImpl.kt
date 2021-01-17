package pl.patrykdolata.lifemanager.service.impl

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import pl.patrykdolata.lifemanager.domain.AccountEntity
import pl.patrykdolata.lifemanager.mapper.AccountMapper
import pl.patrykdolata.lifemanager.model.NewAccount
import pl.patrykdolata.lifemanager.repository.AccountRepository
import pl.patrykdolata.lifemanager.service.AccountService

@Service
class AccountServiceImpl(private val accountRepository: AccountRepository,
                         private val accountMapper: AccountMapper) : AccountService {

    private val log: Logger = LoggerFactory.getLogger(AccountServiceImpl::class.java)

    override fun create(account: NewAccount): Long {
        log.debug("Create new account with name: ${account.name}")
        val newAccount: AccountEntity = accountRepository.save(accountMapper.toEntity(account))

        return newAccount.id!!
    }
}
