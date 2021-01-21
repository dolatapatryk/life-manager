package pl.patrykdolata.lifemanager.domain

import pl.patrykdolata.lifemanager.domain.ExpenseCategoryEntity.Companion.TABLE_NAME
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import javax.validation.constraints.Size

@Entity
@Table(name = TABLE_NAME)
class ExpenseCategoryEntity(user: UserEntity) : AbstractUserDependEntity<Long>(user) {

    companion object {
        const val TABLE_NAME = "expense_category"
    }

    @Size(min = 3, max = 50)
    @Column(name = "name", length = 50)
    var name: String = ""

    @Column(name = "description", length = 100)
    var description: String? = null
}
