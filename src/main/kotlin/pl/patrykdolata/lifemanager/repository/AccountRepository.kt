package pl.patrykdolata.lifemanager.repository

import org.springframework.stereotype.Repository
import pl.patrykdolata.lifemanager.domain.AccountEntity

@Repository
interface AccountRepository : CrudSpecificationRepository<AccountEntity, Long>
