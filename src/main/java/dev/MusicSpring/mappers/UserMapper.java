package dev.MusicSpring.mappers;
import dev.MusicSpring.db.dto.UserDTO;
import dev.MusicSpring.db.entities.auth.AuthUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;


@Component
@Mapper
public interface UserMapper {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "roleId", ignore = true)
//    @Mapping(target = "photo", ignore = true)
//    @Mapping(target = "enabled", ignore = true)
//    @Mapping(target = "password", ignore = true)
//    @Mapping(target = "username", ignore = true)
//    AuthUserEntity updateAuthUserEntity(AuthUserEntity existingUser, AuthUserEntity updatedUser);

    @Mapping(source = "roleId", target = "id")
    @Mapping(source = "fio", target = "fio")
    @Mapping(source = "date", target = "date")
    @Mapping(source = "text", target = "text")
    @Mapping(source = "photo", target = "photo")
    @Mapping(source = "username", target = "username")
    UserDTO toDto(AuthUserEntity source);
}