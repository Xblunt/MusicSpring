package dev.MusicSpring.db.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String fio;
    private String date;
    private String text;
    private String photo;
    private String username;

}
