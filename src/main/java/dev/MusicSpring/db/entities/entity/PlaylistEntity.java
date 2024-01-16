package dev.MusicSpring.db.entities.entity;

import dev.MusicSpring.db.entities.auth.AuthUserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="playlist")
@Getter
@Setter
@NoArgsConstructor

public class PlaylistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private AuthUserEntity user;
//    @ManyToMany
//    @LazyCollection(LazyCollectionOption.TRUE)
//    @JoinTable(
//            name = "playlist_track",
//            joinColumns = @JoinColumn(name = "playlist_id"),
//            inverseJoinColumns = @JoinColumn(name = "track_id")
//    )
//    private Set<TrackEntity> tracks;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL)
    private Set<PTEntity> playlisttrack;
    public PlaylistEntity(Long id, AuthUserEntity user) {
       this.id = id;
        this.user = user;


    }
//    @OneToMany(mappedBy = "playlist")
//    private List<TrackEntity> tracks;
//@OneToMany(mappedBy = "playlists", cascade = CascadeType.ALL)
//private List<PlaylistTrackEntity> playlistTracks;
}

