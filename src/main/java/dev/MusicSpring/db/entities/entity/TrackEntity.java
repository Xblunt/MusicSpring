package dev.MusicSpring.db.entities.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Entity
@Table(name="track")
@Getter
@Setter
@NoArgsConstructor
public class TrackEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;

    private String author;

    private String text;
    @Column(columnDefinition = "TEXT")
    private String file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    private AlbumEntity album;


//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "message_id")
//    private MessageEntity message;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "album_id")
//    private AlbumEntity album;
//    @ManyToMany(mappedBy = "tracks")
//    @LazyCollection(LazyCollectionOption.TRUE)
//    private Set<PlaylistEntity> playlists;

//    public void setAlbum(AlbumEntity album) {
//        this.album = album;
//        if (album != null) {
//            album.addTrack(this);
//        }}
@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
@JoinColumn(name = "message_id", referencedColumnName = "id")
private MessageEntity message;
    @OneToMany(mappedBy = "track", cascade = CascadeType.ALL)
    private Set<PTEntity> playlisttrack2;

public TrackEntity(String name,String author,  String text, String file, AlbumEntity album) {
    this.name = name;
    this.author = author;
    this.text = text;
    this.file = file;
    this.album = album;
}
//    @ManyToOne
//    @JoinColumn(name = "playlist_id")
//    private PlaylistEntity playlist;
//    @OneToMany(mappedBy = "tracks", cascade = CascadeType.ALL)
//    private List<PlaylistTrackEntity> playlistTracks;
}
