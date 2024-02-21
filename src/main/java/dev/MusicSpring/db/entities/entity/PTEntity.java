package dev.MusicSpring.db.entities.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "P_T")
@Getter
@Setter
@NoArgsConstructor
public class PTEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id")
    private PlaylistEntity playlist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "track_id")
    private TrackEntity track;


    public PTEntity(TrackEntity track, PlaylistEntity playlist) {
        this.track = track;
        this.playlist = playlist;
   }
}
