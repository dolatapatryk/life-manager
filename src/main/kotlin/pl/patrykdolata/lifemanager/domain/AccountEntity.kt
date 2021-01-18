package pl.patrykdolata.lifemanager.domain

import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import javax.validation.constraints.Size

@Entity
@Table(name = "account")
class AccountEntity(user: UserEntity) : AbstractUserDependEntity<Long>(user) {

    @Size(min = 3, max = 120)
    @Column(name = "name", length = 120)
    var name: String = ""

    @Column(name = "balance")
    var balance: BigDecimal = BigDecimal.ZERO
}
