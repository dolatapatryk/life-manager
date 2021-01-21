package pl.patrykdolata.lifemanager.domain

import pl.patrykdolata.lifemanager.domain.ExpenseEntity.Companion.TABLE_NAME
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = TABLE_NAME)
class ExpenseEntity(
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "account_id")
        val account: AccountEntity,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "category_id")
        val category: ExpenseCategoryEntity
) : AbstractEntity<Long>() {

    companion object {
        const val TABLE_NAME = "expense"
    }

    @Column(name = "amount")
    var amount: BigDecimal = BigDecimal.ZERO

    @Column(name = "date")
    var date: Long = 0
}
