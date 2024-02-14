package dev.MusicSpring.mappers;

import dev.MusicSpring.db.dto.ChatDTO;
import dev.MusicSpring.db.entities.auth.AuthUserEntity;
import dev.MusicSpring.db.entities.entity.ChatEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface ChatMapper {
    ChatMapper MAPPER = Mappers.getMapper(ChatMapper.class);
    @Mapping(source = "id", target = "id")
    @Mapping(source = "firstUser.id", target = "first_id")
    @Mapping(source = "secondUser.id", target = "second_id")
    @Mapping(source = "chatname", target = "chatname")
    ChatDTO toDto(ChatEntity source);

//    @Named("mapUserToId")
//    default Long mapUserToId(AuthUserEntity user) {
//        return user.getId();
//    }
}
