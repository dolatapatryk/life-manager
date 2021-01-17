package pl.patrykdolata.lifemanager.domain

import java.io.Serializable
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class AbstractEntity<T : Serializable> {

    companion object {
        private val serialVersionUID = -5554308939380869754L
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: T? = null
}
