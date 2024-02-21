package dev.MusicSpring.db.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShortTrack {
    private Long id;
    private String name;
    private String author;
    private String file;
}
