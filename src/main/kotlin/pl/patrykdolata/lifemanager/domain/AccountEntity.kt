package pl.patrykdolata.lifemanager.domain

import pl.patrykdolata.lifemanager.domain.AccountEntity.Companion.TABLE_NAME
import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import javax.validation.constraints.Size

@Entity
@Table(name = TABLE_NAME)
class AccountEntity(user: UserEntity) : AbstractUserDependEntity<Long>(user) {

    companion object {
        const val TABLE_NAME = "account"
    }

    @Size(min = 3, max = 120)
    @Column(name = "name", length = 120)
    var name: String = ""

    @Column(name = "balance")
    var balance: BigDecimal = BigDecimal.ZERO
}
