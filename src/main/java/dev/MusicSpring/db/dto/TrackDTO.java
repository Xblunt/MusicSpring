package dev.MusicSpring.db.dto;


import lombok.AllArgsConstructor;
import lombok.Data;


@Data


@AllArgsConstructor
public class TrackDTO {
    private Long id;
    private String name;
    private String author;
    private String text;
    private String file;
    private Long album_id;
}
