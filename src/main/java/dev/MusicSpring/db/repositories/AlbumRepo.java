package dev.MusicSpring.db.repositories;

import dev.MusicSpring.db.entities.entity.AlbumEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.Set;

public interface AlbumRepo extends PagingAndSortingRepository<AlbumEntity, Long> {
    public Set<AlbumEntity> findAll();
    Optional<AlbumEntity> findById(Long id);

}
