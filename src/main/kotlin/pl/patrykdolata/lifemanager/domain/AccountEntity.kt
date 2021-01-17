package pl.patrykdolata.lifemanager.domain

import java.math.BigDecimal
import javax.persistence.*
import javax.validation.constraints.Size

@Entity
@Table(name = "account")
class AccountEntity(
        @ManyToOne
        @JoinColumn(name = "user_id")
        val user: UserEntity
) : AbstractEntity<Long>() {

    @Size(min = 3, max = 120)
    @Column(name = "name", length = 120)
    var name: String = ""

    @Column(name = "balance")
    var balance: BigDecimal = BigDecimal.ZERO
}
