package dev.MusicSpring.db.repositories;

import dev.MusicSpring.db.entities.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<MessageEntity, Long> {
}
