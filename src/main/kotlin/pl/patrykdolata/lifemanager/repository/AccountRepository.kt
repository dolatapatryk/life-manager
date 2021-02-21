package pl.patrykdolata.lifemanager.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import pl.patrykdolata.lifemanager.domain.AccountEntity
import java.util.*

@Repository
interface AccountRepository : CrudSpecificationRepository<AccountEntity, Long> {

    @Query("from AccountEntity a where a.id = :id")
    override fun findById(@Param("id") id: Long): Optional<AccountEntity>
}
