package pl.patrykdolata.lifemanager.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.Named
import pl.patrykdolata.lifemanager.domain.ExpenseCategoryEntity
import pl.patrykdolata.lifemanager.model.ExpenseCategory
import pl.patrykdolata.lifemanager.security.AuthenticationUtils

@Mapper(componentModel = "spring", imports = [AuthenticationUtils::class])
interface ExpenseCategoryMapper : EntityMapper<ExpenseCategory, ExpenseCategoryEntity> {

    @Mappings(
            Mapping(target = "id", ignore = true),
            Mapping(target = "name", source = "name"),
            Mapping(target = "description", source = "description"),
            Mapping(target = "user", expression = "java(AuthenticationUtils.getCurrentUser())")
    )
    override fun toEntity(model: ExpenseCategory): ExpenseCategoryEntity

    @Mappings(
            Mapping(target = "id", source = "id"),
            Mapping(target = "name", source = "name"),
            Mapping(target = "description", source = "description")
    )
    @Named("toModel")
    override fun toModel(entity: ExpenseCategoryEntity): ExpenseCategory
}
