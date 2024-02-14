package dev.MusicSpring.db.dto;

import dev.MusicSpring.db.entities.entity.TrackEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.aspectj.apache.bcel.classfile.Module;

import java.sql.Time;
import java.util.Optional;

@Data
@AllArgsConstructor
public class MessageDTO {
//    private Long id;
//    private Time timeMess;
    private String textMess;
    private Long second;
    private Long first;
   private Long track_id;
//    private Optional<ShortTrack> track_id;
//    private ChatDTO chat;
}
