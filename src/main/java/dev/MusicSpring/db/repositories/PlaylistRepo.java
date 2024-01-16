package dev.MusicSpring.db.repositories;

import dev.MusicSpring.db.entities.entity.AlbumEntity;
import dev.MusicSpring.db.entities.entity.PlaylistEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.Set;

public interface PlaylistRepo extends PagingAndSortingRepository<PlaylistEntity, Long> {
public Set<PlaylistEntity> findAll();
//    Optional<PlaylistEntity> findById(Long id);
}
