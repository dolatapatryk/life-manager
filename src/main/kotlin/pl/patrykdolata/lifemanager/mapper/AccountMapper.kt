package pl.patrykdolata.lifemanager.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import pl.patrykdolata.lifemanager.domain.AccountEntity
import pl.patrykdolata.lifemanager.model.NewAccount
import pl.patrykdolata.lifemanager.security.AuthenticationUtils

@Mapper(componentModel = "spring", imports = [AuthenticationUtils::class])
interface AccountMapper {

    @Mappings(
            Mapping(target = "id", ignore = true),
            Mapping(target = "balance", source = "startBalance"),
            Mapping(target = "user", expression = "java(AuthenticationUtils.getCurrentUser())")
    )
    fun toEntity(account: NewAccount): AccountEntity
}
