package pl.patrykdolata.lifemanager.domain

import pl.patrykdolata.lifemanager.domain.AuthorityEntity.Companion.TABLE_NAME
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.Size

@Entity
@Table(name = TABLE_NAME)
final class AuthorityEntity {

    companion object {
        const val TABLE_NAME = "authority"
    }

    @Id
    @Column(name = "name", length = 20)
    @Size(max = 20)
    var name: String = ""
}
