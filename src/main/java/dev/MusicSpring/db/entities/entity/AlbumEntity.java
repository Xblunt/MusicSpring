package dev.MusicSpring.db.entities.entity;

import com.sun.istack.NotNull;
import dev.MusicSpring.db.dto.ShortTrack;
import dev.MusicSpring.db.entities.auth.AuthUserEntity;
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

public class AlbumEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name_album;

    @Column(columnDefinition = "TEXT")
    private String picture;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    private List<TrackEntity> tracks;
//    public void addTrack(TrackEntity track) {
//        tracks.add(track);
//    }

    public AlbumEntity(Long id, String name_album, String picture) {
        this.id = id;
        this.name_album = name_album;
        this.picture = picture;


    }
}
