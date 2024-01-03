package dev.MusicSpring.db.repositories;


import dev.MusicSpring.db.entities.auth.AuthUserEntity;
import dev.MusicSpring.db.entities.entity.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepo extends PagingAndSortingRepository<UserEntity, Long> {
    public Set<UserEntity> findAll();
}
