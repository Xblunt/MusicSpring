package dev.MusicSpring.db.repositories;

import dev.MusicSpring.db.dto.ChatDTO;
import dev.MusicSpring.db.entities.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepo extends JpaRepository<ChatEntity, Long> {
}
