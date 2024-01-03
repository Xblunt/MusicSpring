package dev.MusicSpring.db.entities.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String fio;
    @NotNull
    private Date date;
    @NotNull
    private String text;
    @NotNull
    private String photo;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private PlaylistEntity playlist;
}
