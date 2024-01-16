package dev.MusicSpring.db.entities.entity;

import com.sun.istack.NotNull;
import dev.MusicSpring.db.entities.auth.AuthUserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
import java.util.List;

@Entity
@Table(name="chat")
@Getter
@Setter
@NoArgsConstructor

public class ChatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 private  String chatname;
    @ManyToOne
    @JoinColumn(name = "first_id")
    private AuthUserEntity firstUser;

    @ManyToOne
    @JoinColumn(name = "second_id")
    private AuthUserEntity secondUser;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<MessageEntity> message;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", referencedColumnName = "id")
    private SessionEntity sessionEntity;

    public ChatEntity(Long id, AuthUserEntity firstUser, AuthUserEntity secondUser,String chatname) {
        this.id = id;
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.chatname = chatname;

    }
}
