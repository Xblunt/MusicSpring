package dev.MusicSpring.db.repositories;

import dev.MusicSpring.db.entities.entity.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SessionRepo extends JpaRepository<SessionEntity, Long> {
}
