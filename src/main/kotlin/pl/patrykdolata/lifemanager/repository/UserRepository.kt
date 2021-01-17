package pl.patrykdolata.lifemanager.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pl.patrykdolata.lifemanager.domain.UserEntity

@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {

    fun findOneByUsername(username: String): UserEntity?

    fun findOneByEmail(email: String): UserEntity?
}
