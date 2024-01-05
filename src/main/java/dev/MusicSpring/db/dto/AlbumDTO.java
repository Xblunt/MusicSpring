package dev.MusicSpring.db.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AlbumDTO {
    private Long id;
    private String name_album;
    private List<ShortTrack> tracks;
}
