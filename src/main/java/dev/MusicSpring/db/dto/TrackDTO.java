package dev.MusicSpring.db.dto;

import dev.MusicSpring.db.entities.entity.AlbumEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrackDTO {
    private Long id;
    private String name;
    private String author;
}
