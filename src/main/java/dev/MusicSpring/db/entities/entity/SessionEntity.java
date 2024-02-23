package dev.MusicSpring.db.entities.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


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
    private Boolean action;
    private Double currentTimeOnDevice;

    @OneToOne(mappedBy = "sessionEntity", cascade = CascadeType.ALL)
    private ChatEntity chatEntity;

    public SessionEntity(Long id, Double time, Boolean action, Boolean pause, Double currentTimeOnDevice) {
        this.id = id;
        this.time = time;
        this.action = action;
        this.pause = pause;
        this.currentTimeOnDevice=currentTimeOnDevice;
    }
}
