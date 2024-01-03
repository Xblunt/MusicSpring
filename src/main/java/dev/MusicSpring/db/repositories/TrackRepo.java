package dev.MusicSpring.db.repositories;

import dev.MusicSpring.db.entities.entity.AlbumEntity;
import dev.MusicSpring.db.entities.entity.TrackEntity;
import dev.MusicSpring.db.entities.entity.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Set;

public interface TrackRepo extends PagingAndSortingRepository<TrackEntity, Long> {
    public Set<TrackEntity> findAll();
    List<TrackEntity> findByAlbum(AlbumEntity album);
}

