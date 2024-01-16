package dev.MusicSpring.db.repositories;

import dev.MusicSpring.db.dto.ChatDTO;
import dev.MusicSpring.db.entities.auth.AuthUserEntity;
import dev.MusicSpring.db.entities.entity.AlbumEntity;
import dev.MusicSpring.db.entities.entity.ChatEntity;
import dev.MusicSpring.db.entities.entity.TrackEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRepo extends PagingAndSortingRepository<ChatEntity, Long> {
//    Optional<ChatEntity> findByfirstUser(String firstUser);
//    Optional<ChatEntity> findByFirstUserId(Long id);
//    Optional<ChatEntity> findByFirstUserRoleId(Long userId);
List<ChatEntity> findByFirstUserId(Long first_id);
    List<ChatEntity> findBySecondUserId(Long second_id);
    Optional<ChatEntity> findById(Long id);

}

