package dev.MusicSpring.db.repositories;

import dev.MusicSpring.db.entities.auth.AuthUserEntity;
import dev.MusicSpring.db.entities.entity.PTEntity;
import dev.MusicSpring.db.entities.entity.PlaylistEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PlaytRepo extends PagingAndSortingRepository<PTEntity, Long> {
    public Set<PTEntity> findAll();
    Page<PTEntity> findByid (Long playlist_id, Pageable pageable);
    List<PTEntity> findByPlaylistId (Long playlist_id);
    Optional<PTEntity> findById (Long id);
}
