package dev.MusicSpring.db.repositories;

import dev.MusicSpring.db.entities.entity.ChatEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRepo extends PagingAndSortingRepository<ChatEntity, Long> {
    List<ChatEntity> findByFirstUserId(Long first_id);
    List<ChatEntity> findBySecondUserId(Long second_id);
    Optional<ChatEntity> findById(Long id);
}

