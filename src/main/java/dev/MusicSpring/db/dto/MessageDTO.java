package dev.MusicSpring.db.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Time;

@Data
@AllArgsConstructor
public class MessageDTO {
    private Long id;
    private Time timeMess;
    private String textMess;
    private Long friendId;
    private ChatDTO chat;
}
