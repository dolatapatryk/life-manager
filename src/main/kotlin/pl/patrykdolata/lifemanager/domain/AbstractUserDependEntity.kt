package pl.patrykdolata.lifemanager.domain

import org.hibernate.annotations.*
import pl.patrykdolata.lifemanager.domain.AbstractUserDependEntity.Companion.USER_FILTER
import pl.patrykdolata.lifemanager.domain.AbstractUserDependEntity.Companion.USER_ID_PARAMETER
import java.io.Serializable
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.MappedSuperclass

@FilterDefs(
        FilterDef(
                name = USER_FILTER,
                parameters = [ParamDef(name = USER_ID_PARAMETER, type = "long")],
                defaultCondition = "user_id = :${USER_ID_PARAMETER}"
        )
)
@Filters(
        Filter(name = USER_FILTER)
)
@MappedSuperclass
abstract class AbstractUserDependEntity<T : Serializable>(
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        val user: UserEntity
) : AbstractEntity<T>() {

    companion object {
        const val USER_FILTER = "filterByUser"
        const val USER_ID_PARAMETER = "userId"
    }
}
