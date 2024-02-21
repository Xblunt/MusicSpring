package dev.MusicSpring.db.repositories;

import dev.MusicSpring.db.entities.entity.AlbumEntity;
import dev.MusicSpring.db.entities.entity.TrackEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TrackRepo extends PagingAndSortingRepository<TrackEntity, Long> {
    public Set<TrackEntity> findAll();
    Optional<TrackEntity> findByname(String name);
    List<TrackEntity> findByAlbum(AlbumEntity album);
    Optional<TrackEntity> findById(Long id);
    Page<TrackEntity> findAllByAlbum(AlbumEntity album, Pageable pageable);
    Page<TrackEntity> findAllByAlbumIdNot(Long albumIdToExclude, Pageable pageable);

    @Modifying
    @Query("UPDATE TrackEntity t SET t.album = NULL WHERE t.album.id = :albumId")
    void updateAlbumIdForDeletedAlbum(@Param("albumId") Long albumId);
}

