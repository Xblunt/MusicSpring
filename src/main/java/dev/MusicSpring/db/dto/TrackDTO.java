package dev.MusicSpring.db.dto;

import dev.MusicSpring.db.entities.entity.AlbumEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TrackDTO {
    private Long id;
    private String name;
    private String author;
    private String text;
    private String file;
    private List<AlbumDTO> albumId;
}
