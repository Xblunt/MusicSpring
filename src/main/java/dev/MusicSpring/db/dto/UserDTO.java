package dev.MusicSpring.db.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String fio;
    private Date date;
    private String text;
    private String photo;

}
