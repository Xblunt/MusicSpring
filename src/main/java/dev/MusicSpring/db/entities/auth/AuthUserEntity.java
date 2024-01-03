package dev.MusicSpring.db.entities.auth;

import dev.MusicSpring.db.entities.BaseEntity;
import dev.MusicSpring.db.entities.entity.PlaylistEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
public class AuthUserEntity extends BaseEntity {
    static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public AuthUserEntity(boolean enabled, String fio,String username, String password, String date, String text, String photo, Set roles){
        this.password = passwordEncoder.encode(password);
        this.enabled = enabled;
        this.fio = fio;
        this.roles = roles;
        this.text = text;
        this.photo = photo;
        this.date = date;
        this.username = username;
    }

    private String fio;
    private String text;
    private String date;
    private String photo;
    private String username;
    private boolean enabled;
    private String password;
    @OneToMany(cascade={CascadeType.ALL},
            orphanRemoval=true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name="user_id", updatable=true)
    private Set<RoleUserEntity> roles;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private PlaylistEntity playlist;
}
