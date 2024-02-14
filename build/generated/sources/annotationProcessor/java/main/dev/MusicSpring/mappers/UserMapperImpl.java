package dev.MusicSpring.mappers;

import dev.MusicSpring.db.dto.UserDTO;
import dev.MusicSpring.db.entities.auth.AuthUserEntity;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-12T18:49:18+0300",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.jar, environment: Java 1.8.0_382 (Amazon.com Inc.)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO toDto(AuthUserEntity source) {
        if ( source == null ) {
            return null;
        }

        Long id = null;
        String fio = null;
        String date = null;
        String text = null;
        String photo = null;
        String username = null;

        id = source.getRoleId();
        fio = source.getFio();
        date = source.getDate();
        text = source.getText();
        photo = source.getPhoto();
        username = source.getUsername();

        UserDTO userDTO = new UserDTO( id, fio, date, text, photo, username );

        return userDTO;
    }
}
