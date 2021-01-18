package pl.patrykdolata.lifemanager.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.Named
import pl.patrykdolata.lifemanager.domain.AccountEntity
import pl.patrykdolata.lifemanager.model.Account
import pl.patrykdolata.lifemanager.security.AuthenticationUtils

@Mapper(componentModel = "spring", imports = [AuthenticationUtils::class])
interface AccountMapper : EntityMapper<Account, AccountEntity> {

    @Mappings(
            Mapping(target = "id", ignore = true),
            Mapping(target = "balance", source = "balance"),
            Mapping(target = "user", expression = "java(AuthenticationUtils.getCurrentUser())")
    )
    override fun toEntity(model: Account): AccountEntity

    @Mappings(
            Mapping(target = "name", source = "name"),
            Mapping(target = "balance", source = "balance")
    )
    @Named("toModel")
    override fun toModel(entity: AccountEntity): Account

}
