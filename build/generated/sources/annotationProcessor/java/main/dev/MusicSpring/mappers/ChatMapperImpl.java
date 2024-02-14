package dev.MusicSpring.mappers;

import dev.MusicSpring.db.dto.ChatDTO;
import dev.MusicSpring.db.entities.auth.AuthUserEntity;
import dev.MusicSpring.db.entities.entity.ChatEntity;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-12T18:49:18+0300",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.jar, environment: Java 1.8.0_382 (Amazon.com Inc.)"
)
public class ChatMapperImpl implements ChatMapper {

    @Override
    public ChatDTO toDto(ChatEntity source) {
        if ( source == null ) {
            return null;
        }

        Long id = null;
        Long first_id = null;
        Long second_id = null;
        String chatname = null;

        id = source.getId();
        first_id = sourceFirstUserId( source );
        second_id = sourceSecondUserId( source );
        chatname = source.getChatname();

        ChatDTO chatDTO = new ChatDTO( id, first_id, second_id, chatname );

        return chatDTO;
    }

    private Long sourceFirstUserId(ChatEntity chatEntity) {
        if ( chatEntity == null ) {
            return null;
        }
        AuthUserEntity firstUser = chatEntity.getFirstUser();
        if ( firstUser == null ) {
            return null;
        }
        Long id = firstUser.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long sourceSecondUserId(ChatEntity chatEntity) {
        if ( chatEntity == null ) {
            return null;
        }
        AuthUserEntity secondUser = chatEntity.getSecondUser();
        if ( secondUser == null ) {
            return null;
        }
        Long id = secondUser.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
