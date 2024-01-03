package dev.MusicSpring.db.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShortTrack {
    private Long id;
    private String name;
    private String author;
}
