package dev.MusicSpring.db.repositories;

import dev.MusicSpring.db.dto.UserDTO;
import dev.MusicSpring.db.entities.auth.AuthUserEntity;
import dev.MusicSpring.db.entities.entity.AlbumEntity;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface AuthUserRepo extends PagingAndSortingRepository<AuthUserEntity, Long> {
   List<AuthUserEntity> findByUsername(String username);
   Optional<AuthUserEntity> findByUsernameIgnoreCase(String username);
//   Optional<AuthUserEntity> findById(Long id);
   @Modifying
   @Query(value = "UPDATE users u SET u.enabled = false WHERE user_id = :id",
           nativeQuery = true)
   int deleteUser(@Param("id")Long user_id);
;
   Page<AuthUserEntity> findByUsernameNot(String username, Pageable pageable);
   public Set<AuthUserEntity> findAll();





}
