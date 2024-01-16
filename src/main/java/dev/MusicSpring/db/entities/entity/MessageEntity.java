package dev.MusicSpring.db.entities.entity;

import com.sun.istack.NotNull;
import dev.MusicSpring.db.entities.auth.AuthUserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.List;

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
//    @NotNull
//    private Long friend_id;
    @ManyToOne
    @JoinColumn(name = "second_id")
    private AuthUserEntity second;
    @ManyToOne
    @JoinColumn(name = "first_id")
    private AuthUserEntity first;
    @ManyToOne
    @JoinColumn(name = "id_chat")
    private ChatEntity chat;

//    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL)
//    private List<TrackEntity> track;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "track_id", referencedColumnName = "id")
    private TrackEntity track;
    public MessageEntity( String text_mess, ChatEntity chat, AuthUserEntity first, AuthUserEntity second ) {
//        this.secondUser = secondUser;
        this.text_mess = text_mess;
        this.chat = chat;
        this.first = first;
        this.second = second;
    }
}
