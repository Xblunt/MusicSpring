package dev.MusicSpring.db.entities.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
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
}
