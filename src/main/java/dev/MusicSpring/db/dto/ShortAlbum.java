package dev.MusicSpring.db.dto;

import dev.MusicSpring.db.entities.entity.TrackEntity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShortAlbum {
    private Long id;
    private String name_album;
    private  String picture;
}