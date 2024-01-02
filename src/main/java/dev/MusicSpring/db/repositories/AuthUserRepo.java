package dev.MusicSpring.db.repositories;

import dev.MusicSpring.db.entities.auth.AuthUserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AuthUserRepo extends PagingAndSortingRepository<AuthUserEntity, Long> {

   @Modifying
   @Query(value = "UPDATE users u SET u.enabled = false WHERE user_id = :id",
           nativeQuery = true)
   int deleteUser(@Param("id")Long user_id);
;
   public Set<AuthUserEntity> findAll();


}
