package dev.MusicSpring.config;

import dev.MusicSpring.db.entities.auth.AuthUserEntity;
import dev.MusicSpring.db.entities.auth.BaseRole;
import dev.MusicSpring.db.entities.auth.RoleUserEntity;
import dev.MusicSpring.db.entities.entity.AlbumEntity;
import dev.MusicSpring.db.entities.entity.PlaylistEntity;
import dev.MusicSpring.db.entities.entity.TrackEntity;
import dev.MusicSpring.db.repositories.AlbumRepo;
import dev.MusicSpring.db.repositories.AuthUserRepo;
import dev.MusicSpring.db.repositories.PlaylistRepo;
import dev.MusicSpring.db.repositories.TrackRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public void initial() {


        AuthUserEntity admin = new AuthUserEntity(true, "AAA", "admin", "1234", "02.02.2022", "TEXT", "URL",
                Collections.singleton(new RoleUserEntity("admin", BaseRole.SUPER_USER))
        );

        authUserRepo.save(admin);

        AuthUserEntity user1 = new AuthUserEntity(true,  "BBB", "user1", "1234", "02.02.2022", "TEXT", "URL",
                Collections.singleton(new RoleUserEntity("user1", BaseRole.USERS))
        );

        authUserRepo.save(user1);

        AuthUserEntity user2 = new AuthUserEntity(true, "CCC", "user2", "1234", "02.02.2022", "TEXT", "URL",
                Collections.singleton(new RoleUserEntity("user2", BaseRole.USERS))
        );

        authUserRepo.save(user2);
        AuthUserEntity user3 = new AuthUserEntity(true, "GGG", "user3", "1234", "02.02.2022", "TEXT", "URL",
                Collections.singleton(new RoleUserEntity("user3", BaseRole.USERS))
        );

        authUserRepo.save(user3);

        AuthUserEntity user4 = new AuthUserEntity(true, "SSSS", "user4", "1234", "02.02.2022", "TEXT", "URL",
                Collections.singleton(new RoleUserEntity("user4", BaseRole.USERS))
        );

        authUserRepo.save(user4);
        AuthUserEntity user5 = new AuthUserEntity(true, "WWW", "user5", "1234", "02.02.2022", "TEXT", "URL",
                Collections.singleton(new RoleUserEntity("user5", BaseRole.USERS))
        );

        authUserRepo.save(user5);
        AuthUserEntity user6 = new AuthUserEntity(true, "VVV", "user6", "1234", "02.02.2022", "TEXT", "URL",
                Collections.singleton(new RoleUserEntity("user6", BaseRole.USERS))
        );

        authUserRepo.save(user6);


        AlbumEntity album = new AlbumEntity(1L,"ALBUM1","URL",new ArrayList<>());
        AlbumEntity album2 = new AlbumEntity(2L,"ALBUM2","URL", new ArrayList<>());
        albumRepo.save(album);
        albumRepo.save(album2);
        PlaylistEntity playlist = new PlaylistEntity(1L, user1,null);
        PlaylistEntity playlist2 = new PlaylistEntity(2L, user2,null);
        playlistRepo.save(playlist);
        playlistRepo.save(playlist2);

        TrackEntity track1 = new TrackEntity(1L, "TTT1", "AUTHOR", "TEXT", "url", album, playlist);

        TrackEntity track2 = new TrackEntity(2L, "TTT2", "AUTHOR", "TEXT", "url", album, playlist);
        TrackEntity track3 = new TrackEntity(3L, "TTT3", "AUTHOR", "TEXT", "url", album2, playlist2);


        TrackEntity track4 = new TrackEntity(4L, "444", "AUTHOR", "TEXT", "url", album, playlist);

        trackRepo.save(track1);
        trackRepo.save(track2);
        trackRepo.save(track3);
        trackRepo.save(track4);

    }
}


