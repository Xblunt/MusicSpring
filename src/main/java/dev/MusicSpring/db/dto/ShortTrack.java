package dev.MusicSpring.db.dto;

import dev.MusicSpring.db.entities.entity.PlaylistEntity;
//import dev.MusicSpring.db.entities.entity.PlaylistTrackEntity;
import lombok.*;
import java.util.Set;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShortTrack {
    private Long id;

    private String name;
    private String author;
    private String file;

//    private Set<PlaylistTrackEntity> playlistTracks;
}
