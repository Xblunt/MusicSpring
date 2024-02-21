package dev.MusicSpring.db.entities.entity;


import dev.MusicSpring.db.entities.auth.AuthUserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;


@Entity
@Table(name="message")
@Getter
@Setter
@NoArgsConstructor

public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Time time_mess;
    private String text_mess;

    @ManyToOne
    @JoinColumn(name = "second_id")
    private AuthUserEntity second;

    @ManyToOne
    @JoinColumn(name = "first_id")
    private AuthUserEntity first;

    @ManyToOne
    @JoinColumn(name = "id_chat")
    private ChatEntity chat;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "track_id", referencedColumnName = "id")
    private TrackEntity track;
    public MessageEntity( String text_mess, ChatEntity chat, AuthUserEntity first, AuthUserEntity second ) {
        this.text_mess = text_mess;
        this.chat = chat;
        this.first = first;
        this.second = second;
    }
}
