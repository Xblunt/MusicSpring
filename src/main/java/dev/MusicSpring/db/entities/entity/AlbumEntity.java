package dev.MusicSpring.db.entities.entity;

import com.sun.istack.NotNull;
import dev.MusicSpring.db.dto.ShortTrack;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="album")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlbumEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name_album;

    @NotNull
    private String picture;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    private List<TrackEntity> tracks;
//    public void addTrack(TrackEntity track) {
//        tracks.add(track);
//    }
}
