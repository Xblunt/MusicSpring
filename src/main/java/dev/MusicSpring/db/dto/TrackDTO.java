package dev.MusicSpring.db.dto;

import dev.MusicSpring.db.entities.entity.PlaylistEntity;
//import dev.MusicSpring.db.entities.entity.PlaylistTrackEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class TrackDTO {
    private Long id;
    private String name;
    private String author;
    private String text;
    private String file;
    private Long album_id;
//    private List<PlaylistTrackEntity> playlistTracks;
}
