package dev.vorstu.config;

import dev.vorstu.db.entities.auth.AuthUserEntity;
import dev.vorstu.db.entities.auth.BaseRole;
import dev.vorstu.db.entities.auth.RoleUserEntity;
import dev.vorstu.db.repositories.AuthUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class Initializer {
    @Autowired
    private AuthUserRepo authUserRepo;

    public void initial() {


        AuthUserEntity admin = new AuthUserEntity(true, "admin", "1234", "Andrey", "Abramov",
                Collections.singleton(new RoleUserEntity("admin", BaseRole.SUPER_USER))
        );

        authUserRepo.save(admin);

        AuthUserEntity user1 = new AuthUserEntity(true,  "user1", "123456","Anatoliy", "Stark",
                Collections.singleton(new RoleUserEntity("user1", BaseRole.STUDENT))
        );

        authUserRepo.save(user1);

        AuthUserEntity user2 = new AuthUserEntity(true, "user2", "123456","Victor", "Tsoy",
                Collections.singleton(new RoleUserEntity("user2", BaseRole.STUDENT))
        );

        authUserRepo.save(user2);
        AuthUserEntity user3 = new AuthUserEntity(true, "user3", "123456","Santa", "Klays",
                Collections.singleton(new RoleUserEntity("user3", BaseRole.STUDENT))
        );

        authUserRepo.save(user3);

        AuthUserEntity user4 = new AuthUserEntity(true, "user4", "123456","Kurtka", "Beina",
                Collections.singleton(new RoleUserEntity("user4", BaseRole.STUDENT))
        );

        authUserRepo.save(user4);
        AuthUserEntity user5 = new AuthUserEntity(true, "user5", "123456","Brus", "Wane",
                Collections.singleton(new RoleUserEntity("user5", BaseRole.STUDENT))
        );

        authUserRepo.save(user5);
        AuthUserEntity user6 = new AuthUserEntity(true, "user6", "123456","Uriy", "Boyko",
                Collections.singleton(new RoleUserEntity("user6", BaseRole.STUDENT))
        );

        authUserRepo.save(user6);

        AuthUserEntity teach = new AuthUserEntity(true, "teach", "1111","Margo", "Van",
                Collections.singleton(new RoleUserEntity("teach", BaseRole.TEACHER))
        );
        authUserRepo.save(teach);

    }
}


