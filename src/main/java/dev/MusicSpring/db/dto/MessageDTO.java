package dev.MusicSpring.db.dto;


import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class MessageDTO {
    private String textMess;
    private Long second;
    private Long first;
    private Long track_id;
}
