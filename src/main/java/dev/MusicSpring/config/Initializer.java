package dev.MusicSpring.config;

import dev.MusicSpring.db.entities.auth.AuthUserEntity;
import dev.MusicSpring.db.entities.auth.BaseRole;
import dev.MusicSpring.db.entities.auth.RoleUserEntity;
import dev.MusicSpring.db.entities.entity.*;
import dev.MusicSpring.db.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.Collections;


@Component
public class Initializer {
    @Autowired
    private AuthUserRepo authUserRepo;
    @Autowired
    private TrackRepo trackRepo;
    @Autowired
    private AlbumRepo albumRepo;
    @Autowired
    private PlaylistRepo playlistRepo;
    @Autowired
    private SessionRepo sessionRepo;
    @Autowired
    private ChatRepo chatRepo;
    @Autowired
    private MessageRepo messageRepo;
    @Autowired
    private PlaytRepo playtRepo;


    public void initial() {

        String address = "http://127.0.0.1:9000/tracks";
        AuthUserEntity admin = new AuthUserEntity(true, 1L, "Andrey", "admin", "1234", "02.02.2022", "Люблю пиццу", address+"/01.jpg",
                Collections.singleton(new RoleUserEntity("admin", BaseRole.SUPER_USER))
        );

        authUserRepo.save(admin);

        AuthUserEntity user1 = new AuthUserEntity(true, 2L, "Misha", "user1", "1234", "02.02.2022", "Случшаю музыку", address+"/01.jpg",
                Collections.singleton(new RoleUserEntity("user1", BaseRole.CLIENT))
        );

        authUserRepo.save(user1);

        AuthUserEntity user2 = new AuthUserEntity(true,3L,  "Daniil",  "user2", "1234", "02.02.2022", "Я помню.не помню", address+"/02.jpg",
                Collections.singleton(new RoleUserEntity("user2", BaseRole.CLIENT))
        );

        authUserRepo.save(user2);
        AuthUserEntity user3 = new AuthUserEntity(true, 4L, "Grigory", "user3", "1234", "02.02.2022", "ЯЯЯЯЯ устал", address+"/06.jpg",
                Collections.singleton(new RoleUserEntity("user3", BaseRole.CLIENT))
        );

        authUserRepo.save(user3);

        AuthUserEntity user4 = new AuthUserEntity(true, 5L, "Sergey", "user4", "1234", "02.02.2022", "Быть или не быть", address+"/07.jpg",
                Collections.singleton(new RoleUserEntity("user4", BaseRole.CLIENT))
        );

        authUserRepo.save(user4);
        AuthUserEntity user5 = new AuthUserEntity(true, 6L,"Kirill", "user5", "1234", "02.02.2022", "Распутин мой кумир", address+"/07.jpg",
                Collections.singleton(new RoleUserEntity("user5", BaseRole.CLIENT))
        );

        authUserRepo.save(user5);
        AuthUserEntity user6 = new AuthUserEntity(true, 7L, "Vova", "user6", "1234", "02.02.2022", "Напишите сами", address+"/07.jpg",
                Collections.singleton(new RoleUserEntity("user6", BaseRole.CLIENT))
        );

        authUserRepo.save(user6);
        AuthUserEntity user7 = new AuthUserEntity(true, 8L, "VVV", "user6", "1234", "02.02.2022", "АГА, вот ты и попался", address+"/07.jpg",
                Collections.singleton(new RoleUserEntity("user6", BaseRole.CLIENT))
        );

        authUserRepo.save(user7);


        AlbumEntity album = new AlbumEntity(1L,"2001 - Mutter",address+"/2001%20-%20Mutter.jpg");
        AlbumEntity album2 = new AlbumEntity(2L,"1997 - Sehnsucht",address+"/1997%20-%20Sehnsucht.jpg");
        albumRepo.save(album);
        albumRepo.save(album2);
        PlaylistEntity playlist = new PlaylistEntity(1L, admin);
        PlaylistEntity playlist2 = new PlaylistEntity(2L, user1);
        PlaylistEntity playlist3 = new PlaylistEntity(3l, user2);

        SessionEntity session1 = new SessionEntity(1L, null, null, false);
        SessionEntity session2 = new SessionEntity(2L, null, null, false);
        sessionRepo.save(session2);
        SessionEntity session3 = new SessionEntity(3L, null, null, false);
        sessionRepo.save(session3);
        ChatEntity chat1 = new ChatEntity(1L, user1, user2, "chat1", session1 );

        ChatEntity chat2 = new ChatEntity(2L, user1, user3, "chat2", session2);
        ChatEntity chat3 = new ChatEntity(3L, user2, user3, "chat3", session3);

        chatRepo.save(chat1);
        sessionRepo.save(session1);
        chatRepo.save(chat2);
        chatRepo.save(chat3);
        MessageEntity mess1 = new MessageEntity("Привет, ты знаешь что такое безумие?", chat1, user1,user2 );
        MessageEntity mess2 = new MessageEntity("Расскажи мне!",chat1,user1,user2 );
        MessageEntity mess3 = new MessageEntity("Сам давай", chat2,user1,user3 );
        MessageEntity mess4 = new MessageEntity("Не хочу", chat2,user1,user3 );
        MessageEntity mess5 = new MessageEntity("Пока", chat3,user2,user3 );
        MessageEntity mess6 = new MessageEntity("Послушаем музыку?", chat3,user2,user3 );
        messageRepo.save(mess1);
        messageRepo.save(mess2);
        messageRepo.save(mess3);
        messageRepo.save(mess4);
        messageRepo.save(mess5);
        messageRepo.save(mess6);



        TrackEntity track1 = new TrackEntity( "Amerika", "Rammstein", "Тут могло быть ваше описание или реклама", address+"/Amerika.mp3",album);

        TrackEntity track2 = new TrackEntity( "Ausländer", "Rammstein", "Тут могла быть ваша реклама", address+"/Ausl%C3%A4nder.mp3", album);
        TrackEntity track3 = new TrackEntity( "Mann gegen Mann", "Rammstein", "Тут могла быть ваша реклама", address+"/Rammstein%20-%20Mann%20gegen%20Mann.mp3", album2);


        TrackEntity track4 = new TrackEntity( "Rein raus", "Rammstein", "Тут могла быть ваша реклама", address+"/Rammstein%20-%20Rein%20raus.mp3", album);

        trackRepo.save(track1);
        trackRepo.save(track2);
        trackRepo.save(track3);
        trackRepo.save(track4);



        playlistRepo.save(playlist);
        playlistRepo.save(playlist2);
        playlistRepo.save(playlist3);
        PTEntity ptt1 = new PTEntity(track1,playlist2);
        PTEntity ptt2 = new PTEntity(track2,playlist2);
        PTEntity ptt3 = new PTEntity(track1,playlist3);
        PTEntity ptt4 = new PTEntity(track2,playlist3);
        PTEntity ptt5 = new PTEntity(track3,playlist3);
        PTEntity ptt6 = new PTEntity(track4,playlist3);

        playtRepo.save(ptt1);
        playtRepo.save(ptt2);
        playtRepo.save(ptt3);
        playtRepo.save(ptt4);
        playtRepo.save(ptt5);
        playtRepo.save(ptt6);

    }
}


