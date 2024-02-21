package dev.MusicSpring.db.repositories;

import dev.MusicSpring.db.entities.entity.ChatEntity;
import dev.MusicSpring.db.entities.entity.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SessionRepo extends JpaRepository<SessionEntity, Long> {
}
