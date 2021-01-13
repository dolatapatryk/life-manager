package pl.patrykdolata.lifemanager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.patrykdolata.lifemanager.domain.AccountEntity;
import pl.patrykdolata.lifemanager.model.NewAccount;
import pl.patrykdolata.lifemanager.security.AuthenticationUtils;

@Mapper(componentModel = "spring", imports = {AuthenticationUtils.class})
public interface AccountMapper {

    @Mapping(target = "name", source = "name")
    @Mapping(target = "balance", source = "startBalance")
    @Mapping(target = "user", expression = "java(AuthenticationUtils.getCurrentUser())")
    AccountEntity toEntity(NewAccount account);
}
