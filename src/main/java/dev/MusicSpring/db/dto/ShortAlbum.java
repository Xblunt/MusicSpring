package dev.MusicSpring.db.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShortAlbum {
    private Long id;
    private String name_album;
    private  String picture;
}