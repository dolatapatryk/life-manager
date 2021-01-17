package pl.patrykdolata.lifemanager.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pl.patrykdolata.lifemanager.domain.AccountEntity

@Repository
interface AccountRepository : JpaRepository<AccountEntity, Long>
