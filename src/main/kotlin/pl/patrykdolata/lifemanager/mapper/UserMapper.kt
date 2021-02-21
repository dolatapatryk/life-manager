package pl.patrykdolata.lifemanager.mapper

import org.apache.commons.lang3.RandomStringUtils
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.Named
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import pl.patrykdolata.lifemanager.domain.AuthorityEntity
import pl.patrykdolata.lifemanager.domain.UserEntity
import pl.patrykdolata.lifemanager.model.NewUser
import java.util.*

@Mapper(componentModel = "spring", imports = [Date::class, RandomStringUtils::class])
abstract class UserMapper {

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    companion object {
        const val KEY_LENGTH = 20
    }

    @Mappings(
            Mapping(target = "id", ignore = true),
            Mapping(target = "password", source = "newUser.password", qualifiedByName = ["mapPassword"]),
            Mapping(target = "activated", constant = "true"),
            Mapping(target = "activationKey", expression = "java(RandomStringUtils.randomNumeric(KEY_LENGTH))"),
            Mapping(target = "createdAt", expression = "java(new Date().getTime())"),
            Mapping(target = "resetKey", ignore = true),
            Mapping(target = "authorities", source = "isAdmin", qualifiedByName = ["getAuthorities"])
    )
    abstract fun toEntity(newUser: NewUser, isAdmin: Boolean = false): UserEntity

    @Named("mapPassword")
    fun mapPassword(password: String): String {
        return when (password.isEmpty()) {
            true -> passwordEncoder.encode(RandomStringUtils.randomAlphanumeric(KEY_LENGTH))
            false -> passwordEncoder.encode(password)
        }
    }

    @Named("getAuthorities")
    fun getAuthorities(isAdmin: Boolean): Set<AuthorityEntity> {
        val userAuthority = AuthorityEntity("USER")
        return if (isAdmin) return setOf(userAuthority, AuthorityEntity("ADMIN")) else setOf(userAuthority)
    }
}
