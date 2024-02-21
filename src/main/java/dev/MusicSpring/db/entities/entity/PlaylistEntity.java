package dev.MusicSpring.db.entities.entity;

import dev.MusicSpring.db.entities.auth.AuthUserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
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

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL)
    private Set<PTEntity> playlisttrack;

    public PlaylistEntity(Long id, AuthUserEntity user) {
       this.id = id;
       this.user = user;
    }
}

