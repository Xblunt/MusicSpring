package dev.MusicSpring.service;

import dev.MusicSpring.db.dto.*;
import dev.MusicSpring.db.entities.auth.AuthUserEntity;
import dev.MusicSpring.db.entities.entity.AlbumEntity;
import dev.MusicSpring.db.entities.entity.TrackEntity;
import dev.MusicSpring.db.entities.entity.UserEntity;
import dev.MusicSpring.db.repositories.AlbumRepo;
import dev.MusicSpring.db.repositories.AuthUserRepo;
import dev.MusicSpring.db.repositories.TrackRepo;
//import dev.MusicSpring.db.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService {
    @Autowired
    private AuthUserRepo authUserRepo;
    @Autowired
    private TrackRepo trackRepo;
    @Autowired
    private AlbumRepo albumRepo;

    public Page<UserDTO> getAllUsers(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return authUserRepo.findAll(pageRequest)
                .map(el -> new UserDTO(el.getRoleId(), el.getFio(),  el.getText(), el.getDate(), el.getPhoto()));
    }

    public Page<TrackDTO> getAllTracks(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return trackRepo.findAll(pageRequest)
                .map(el -> new TrackDTO(el.getId(), el.getName(), el.getAuthor(), el.getText(), el.getFile(),  el.getAlbum().getId()));
    }

    public Page<ShortAlbum> getAllAlbums(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return albumRepo.findAll(pageRequest)
                .map(el -> new ShortAlbum(el.getId(), el.getName_album(), el.getPicture()));
    }

//    public Page<AlbumDTO> getAllTracksAlbums(int page, int size, Long id) {
//        AlbumEntity album = albumRepo.findById(id)
//                .orElseThrow(() -> new RuntimeException("Альбом не найден: " + id));
//        List<AlbumEntity> albumList = new ArrayList<>();
//        albumList.add(album);
//        PageRequest pageRequest = PageRequest.of(page, size);
//        return new PageImpl<>(albumList)
//                .map(el -> new AlbumDTO(el.getId(), el.getName_album(),
//                        el.getTracks().stream()
//                                .map(track -> new ShortTrack(track.getId(), track.getName(), track.getAuthor()))
//                                .collect(Collectors.toList())));
//    }
public Page<ShortTrack> getAllTracksAlbums(int page, int size, Long id) {
    AlbumEntity album = albumRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Альбом не найден: " + id));


    List<ShortTrack> trackList = album.getTracks().stream()
            .map(track -> new ShortTrack(track.getId(), track.getName(), track.getAuthor()))
            .collect(Collectors.toList());

    int startIndex = page * size;
    int endIndex = Math.min(startIndex + size, trackList.size());

    List<ShortTrack> pagedTrackList = trackList.subList(startIndex, endIndex);

    return new PageImpl<>(pagedTrackList, PageRequest.of(page, size), trackList.size());
}

    public Long deleteUser(Long userId) {
        authUserRepo.deleteById(userId);
        return userId;
    }
    public Long deleteTrack(Long trackId) {
        trackRepo.deleteById(trackId);
        return trackId;
    }
//public Long deleteTrack(Long trackId) {
//    TrackEntity deletedTrack = trackRepo.findById(trackId).orElse(null);
//    if (deletedTrack != null) {
//        Long albumId = deletedTrack.getAlbumId();
//        trackRepo.deleteById(trackId);
//
//
//        if (albumId != null) {
//            trackRepo.updateAlbumIdForDeletedAlbum(albumId);
//        }
//    }
//
//    return trackId;
//}
    public Long deleteAlbum(Long albumId) {
        albumRepo.deleteById(albumId);
        trackRepo.updateAlbumIdForDeletedAlbum(albumId);
        return albumId;
    }
    public AuthUserEntity createUser(AuthUserEntity user) {
        return authUserRepo.save(user);
    }
    public TrackEntity createTrack(TrackEntity track) {
        return trackRepo.save(track);
    }
    public AlbumEntity createAlbum(AlbumEntity album) {
        return albumRepo.save(album);
    }
    public AuthUserEntity changeUser(Long id, AuthUserEntity updatedUser) {
        AuthUserEntity existingUser = authUserRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден: " + id));

        existingUser.setFio(updatedUser.getFio());
        existingUser.setText(updatedUser.getText());
        existingUser.setDate(updatedUser.getDate());
        existingUser.setPhoto(updatedUser.getPhoto());

        return authUserRepo.save(existingUser);
    }
    public TrackEntity changeTrack(Long id, TrackEntity updatedTrack) {
        TrackEntity existingTrack = trackRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Песня не найдена: " + id));

        existingTrack.setName(updatedTrack.getName());
        existingTrack.setAuthor(updatedTrack.getAuthor());

        return trackRepo.save(existingTrack);
    }

    public AlbumEntity changeAlbum(Long id, AlbumEntity updatedAlbum) {
        AlbumEntity existingAlbum = albumRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Альбом не найден: " + id));

        existingAlbum.setName_album(updatedAlbum.getName_album());
        existingAlbum.setPicture(updatedAlbum.getPicture());

        return albumRepo.save(existingAlbum);
    }
}

