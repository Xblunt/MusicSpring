package dev.MusicSpring.db.dto;

import dev.MusicSpring.db.entities.entity.TrackEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ShortAlbum {
    private Long id;
    private String nameAlbum;
    private  String picture;
}