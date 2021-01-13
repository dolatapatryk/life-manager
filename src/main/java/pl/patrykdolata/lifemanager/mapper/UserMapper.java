package pl.patrykdolata.lifemanager.mapper;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.patrykdolata.lifemanager.domain.UserEntity;
import pl.patrykdolata.lifemanager.model.NewUser;

import java.util.Date;

@Mapper(componentModel = "spring", imports = {RandomStringUtils.class, Date.class})
public abstract class UserMapper {

    public static final int KEY_LENGTH = 20;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Mapping(target = "password", source = "password", qualifiedByName = {"mapPassword"})
    @Mapping(target = "activated", constant = "false")
    @Mapping(target = "activationKey", expression = "java(RandomStringUtils.randomNumeric(KEY_LENGTH))")
    @Mapping(target = "createdAt", expression = "java(new Date().getTime())")
    public abstract UserEntity toEntity(NewUser newUser);

    @Named("mapPassword")
    protected String mapPassword(String password) {
        if (StringUtils.isEmpty(password)) {
            return passwordEncoder.encode(RandomStringUtils.randomAlphanumeric(KEY_LENGTH));
        }
        return passwordEncoder.encode(password);
    }
}
