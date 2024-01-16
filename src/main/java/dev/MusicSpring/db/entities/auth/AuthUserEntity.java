package dev.MusicSpring.db.entities.auth;

import dev.MusicSpring.db.entities.BaseEntity;
import dev.MusicSpring.db.entities.entity.ChatEntity;
import dev.MusicSpring.db.entities.entity.MessageEntity;
import dev.MusicSpring.db.entities.entity.PlaylistEntity;
import dev.MusicSpring.db.entities.entity.TrackEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
public class AuthUserEntity extends BaseEntity {
    static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public AuthUserEntity(boolean enabled,Long id, String fio,String username, String password, String date, String text, String photo, Set roles){
        this.password = passwordEncoder.encode(password);
        this.enabled = enabled;
        this.id = id;
        this.fio = fio;
        this.roles = roles;
        this.text = text;
        this.photo = photo;
        this.date = date;
        this.username = username;

    }

    private String fio;
    private Long id;
    private String text;
    private String date;
    @Column(columnDefinition = "TEXT")
    private String photo;
    @Column(name = "username")
    private String username;
    private boolean enabled;
    private String password;

    @OneToMany(mappedBy = "second", cascade = CascadeType.ALL)
    private List<MessageEntity> message;
    @OneToMany(mappedBy = "first", cascade = CascadeType.ALL)
    private List<MessageEntity> message2;
    @OneToMany(cascade={CascadeType.ALL},
            orphanRemoval=true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name="user_id", updatable=true)
    private Set<RoleUserEntity> roles;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private List<ChatEntity> firstUser;
    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private List<ChatEntity> secondUser;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private PlaylistEntity playlist;
}
