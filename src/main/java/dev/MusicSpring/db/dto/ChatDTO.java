package dev.MusicSpring.db.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatDTO {
    private Long id;
    private UserDTO firstUser;
    private UserDTO secondUser;
    private SessionDTO session;
}
