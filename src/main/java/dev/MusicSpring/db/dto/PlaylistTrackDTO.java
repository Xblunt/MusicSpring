package dev.MusicSpring.db.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlaylistTrackDTO {
    private Long id;
    private Long playlistId;
    private Long trackId;
}
