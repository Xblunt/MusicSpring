package dev.MusicSpring.db.dto;

import dev.MusicSpring.db.entities.auth.AuthUserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatDTO {
    private Long id;
    private Long first_id;
    private  Long second_id;
    private String chatname;
    private  Long sessioId;
//    private SessionDTO session;
}
