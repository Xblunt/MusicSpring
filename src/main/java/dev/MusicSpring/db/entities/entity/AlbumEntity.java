package dev.MusicSpring.db.entities.entity;

import com.sun.istack.NotNull;
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


    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    private List<TrackEntity> tracks;


}
