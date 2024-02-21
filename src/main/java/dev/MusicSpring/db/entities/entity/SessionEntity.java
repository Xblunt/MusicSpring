package dev.MusicSpring.db.entities.entity;

import com.sun.istack.NotNull;
import dev.MusicSpring.db.entities.auth.AuthUserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name="session")
@Getter
@Setter
@NoArgsConstructor

public class SessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean pause;

    private Double time;

    private String action;
    @OneToOne(mappedBy = "sessionEntity", cascade = CascadeType.ALL)
    private ChatEntity chatEntity;

    public SessionEntity(Long id, Double time, String action, Boolean pause) {
        this.id = id;
        this.time = time;
        this.action = action;

        this.pause = pause;

    }
}
