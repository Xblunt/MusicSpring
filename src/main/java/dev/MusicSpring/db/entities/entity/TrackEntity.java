package dev.MusicSpring.db.entities.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="track")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrackEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;
    @NotNull
    private String author;
    @NotNull
    private String text;
    @NotNull
    private String file;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id", referencedColumnName = "id")
    private AlbumEntity album;
    @ManyToOne
    @JoinColumn(name = "playlist_id")
    private PlaylistEntity playlist;
}
