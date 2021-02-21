package pl.patrykdolata.lifemanager.domain

import pl.patrykdolata.lifemanager.domain.UserEntity.Companion.TABLE_NAME
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.Size

@Entity
@Table(name = TABLE_NAME)
class UserEntity : AbstractEntity<Long>() {

    companion object {
        const val TABLE_NAME = "user"
    }

    @Size(min = 3, max = 50)
    @Column(name = "username", length = 50, unique = true)
    var username: String = ""

    @Size(min = 60, max = 60)
    @Column(name = "password", length = 60)
    var password: String = ""

    @Size(min = 3, max = 50)
    @Column(name = "first_name", length = 50)
    var firstName: String = ""

    @Size(min = 3, max = 50)
    @Column(name = "last_name", length = 50)
    var lastName: String = ""

    @Email
    @Size(min = 5, max = 254)
    @Column(name = "email", length = 254, unique = true)
    var email: String = ""

    @Column(name = "activated")
    var activated: Boolean = false

    @Size(max = 30)
    @Column(name = "activation_key", length = 30)
    var activationKey: String? = null

    @Size(max = 30)
    @Column(name = "reset_key", length = 30)
    var resetKey: String? = null

    @Column(name = "created_at")
    var createdAt: Long = 0

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
            inverseJoinColumns = [JoinColumn(name = "authority", referencedColumnName = "name")]
    )
    var authorities: Set<AuthorityEntity> = emptySet()
}
