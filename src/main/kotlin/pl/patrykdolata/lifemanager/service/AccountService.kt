package pl.patrykdolata.lifemanager.service

import pl.patrykdolata.lifemanager.domain.AccountEntity
import pl.patrykdolata.lifemanager.model.Account

interface AccountService : CrudService<Account, AccountEntity, Long>
