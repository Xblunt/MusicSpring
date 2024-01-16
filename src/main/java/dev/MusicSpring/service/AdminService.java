package dev.MusicSpring.service;

import dev.MusicSpring.db.dto.*;
import dev.MusicSpring.db.entities.auth.AuthUserEntity;
import dev.MusicSpring.db.entities.entity.AlbumEntity;
import dev.MusicSpring.db.entities.entity.PlaylistEntity;
import dev.MusicSpring.db.entities.entity.TrackEntity;
import dev.MusicSpring.db.entities.entity.UserEntity;
import dev.MusicSpring.db.repositories.AlbumRepo;
import dev.MusicSpring.db.repositories.AuthUserRepo;
import dev.MusicSpring.db.repositories.PlaylistRepo;
import dev.MusicSpring.db.repositories.TrackRepo;
//import dev.MusicSpring.db.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AdminService {
    @Autowired
    private AuthUserRepo authUserRepo;
    @Autowired
    private TrackRepo trackRepo;
    @Autowired
    private AlbumRepo albumRepo;
    private PlaylistRepo playlistRepo;

    public Page<UserDTO> getAllUsers(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return authUserRepo.findAll(pageRequest)
                .map(el -> new UserDTO(el.getRoleId(), el.getFio(), el.getDate()  , el.getText(), el.getPhoto(), el.getUsername()));
    }

    public Page<TrackDTO> getAllTracks(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return trackRepo.findAll(pageRequest)

                .map(el -> new TrackDTO(el.getId(), el.getName(), el.getAuthor(), el.getText(), el.getFile(),  el.getAlbum().getId()));


    }
//    public Page<TrackDTO> getAllTracksAdd(int page, int size) {
//        PageRequest pageRequest = PageRequest.of(page, size);
//        return trackRepo.findAll(pageRequest)
//
//                .map(el -> new TrackDTO(el.getId(), el.getName(), el.getAuthor(), el.getText(), el.getFile(),  el.getAlbum().getId()));
//    }

public Page<TrackDTO> getAllTracksAdd(int page, int size, Long albumIdToExclude) {

    Set<TrackEntity> allTracksSet = trackRepo.findAll();

    List<TrackEntity> allTracks = new ArrayList<>(allTracksSet);

    List<TrackEntity> filteredTracks = allTracks.stream()
            .filter(track -> !track.getAlbum().getId().equals(albumIdToExclude))
            .collect(Collectors.toList());

    List<TrackEntity> tracksForPage = filteredTracks.subList(page * size, Math.min((page * size) + size, filteredTracks.size()));
    List<TrackDTO> trackDTOs = tracksForPage.stream()
            .map(track -> new TrackDTO(
                    track.getId(),
                    track.getName(),
                    track.getAuthor(),
                    track.getText(),
                    track.getFile(),
                    track.getAlbum().getId()


            ))
            .collect(Collectors.toList());


    Pageable pageable = PageRequest.of(page, size);

    return new PageImpl<>(trackDTOs, pageable, filteredTracks.size());
}
    public Optional<TrackDTO> getAllTracksByTrackId(Long trackId,  int page, int size) {

        return trackRepo.findById(trackId)
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

    List<ShortTrack> pagedTrackList = trackList.subList(page * size, Math.min((page * size) + size, trackList.size()));
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

    public Long deleteAlbum(Long albumId) {
        albumRepo.deleteById(albumId);
        trackRepo.updateAlbumIdForDeletedAlbum(albumId);
        return albumId;
    }
    public AuthUserEntity createUser(AuthUserEntity user) {
        return authUserRepo.save(user);
    }
//    public TrackEntity createTrack(TrackEntity track) {
//        return trackRepo.save(track);
//    }
public TrackEntity createTrack(Long id, TrackEntity track) {
    AlbumEntity album = albumRepo.findById(id).orElseThrow(() -> new RuntimeException("Album not found"));
    track.setAlbum(album);
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

    public TrackEntity updateTrack(Long id, TrackDTO trackDto) {
//        Optional<TrackEntity> optionalTrack = trackRepo.findById(id);
//
//
//
//        TrackEntity track = optionalTrack.get();
        TrackEntity track = trackRepo.findById(id)
               .orElseThrow(() -> new RuntimeException("Track not found: " + id));
        track.setName(trackDto.getName());
        track.setAuthor(trackDto.getAuthor());
        track.setText(trackDto.getText());
        track.setFile(trackDto.getFile());

        Long newAlbumId = trackDto.getAlbum_id();

//        Long newPlayId = trackDto.getAlbum_id();
//        PlaylistEntity newPlay = playlistRepo.findById(newPlayId)
//                .orElseThrow(() -> new RuntimeException("Album not found: " + newPlayId));
//
//
//        track.setPlaylists()(newPlay);
        AlbumEntity newAlbum = albumRepo.findById(newAlbumId)
                .orElseThrow(() -> new RuntimeException("Album not found: " + newAlbumId));

//            Optional<AlbumEntity> optionalAlbum = albumRepo.findById(newAlbumId);


        track.setAlbum(newAlbum);
//            track.setAlbum(optionalAlbum.get());


        return trackRepo.save(track);
    }
//    public TrackEntity updateTrackAlbum(Long id, Long albumid, TrackDTO trackDto) {
////        Optional<TrackEntity> optionalTrack = trackRepo.findById(id);
////
////
////
////        TrackEntity track = optionalTrack.get();
//        TrackEntity track = trackRepo.findById(id)
//                .orElseThrow(() -> new RuntimeException("Track not found: " + id));
//        track.setName(trackDto.getName());
//        track.setAuthor(trackDto.getAuthor());
//        track.setText(trackDto.getText());
//        track.setFile(trackDto.getFile());
//        AlbumEntity album = albumRepo.findById(albumid)
//                .orElseThrow(() -> new RuntimeException("Альбом не найден: " + albumid));
//        Long newAlbumId = trackDto.getAlbum_id();
//
//
//        AlbumEntity newAlbum = albumRepo.findById(newAlbumId)
//                .orElseThrow(() -> new RuntimeException("Album not found: " + newAlbumId));
//
////            Optional<AlbumEntity> optionalAlbum = albumRepo.findById(newAlbumId);
//
//
//        track.setAlbum(newAlbum);
////            track.setAlbum(optionalAlbum.get());
//
//
//        return trackRepo.save(track);
//    }

    public TrackEntity updateTrackAlbum(Long id, Long albumId, TrackDTO trackDto) {
        TrackEntity track = trackRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Track not found: " + id));

        track.setName(trackDto.getName());
        track.setAuthor(trackDto.getAuthor());
        track.setText(trackDto.getText());
        track.setFile(trackDto.getFile());
//        Long newPlayId = trackDto.getAlbum_id();
//        PlaylistEntity newPlay = playlistRepo.findById(newPlayId)
//                .orElseThrow(() -> new RuntimeException("Album not found: " + newPlayId));
//
//
//        track.setPlaylist(newPlay);
        Long newAlbumId = trackDto.getAlbum_id();
        AlbumEntity newAlbum = albumRepo.findById(newAlbumId)
                .orElseThrow(() -> new RuntimeException("Album not found: " + newAlbumId));


        track.setAlbum(newAlbum);

        return trackRepo.save(track);
    }
//    public TrackEntity changeTrack(Long id, TrackEntity updatedTrack, String albumIdStr) {
//        TrackEntity existingTrack = trackRepo.findById(id)
//                .orElseThrow(() -> new RuntimeException("Track not found: " + id));
//        Long albumId = Long.parseLong(albumIdStr);
//        AlbumEntity newAlbum = albumRepo.findById(albumId)
//                .orElseThrow(() -> new RuntimeException("Album not found: " + albumId));
//
//        existingTrack.setName(updatedTrack.getName());
//        existingTrack.setAuthor(updatedTrack.getAuthor());
//        existingTrack.setText(updatedTrack.getText());
//        existingTrack.setFile(updatedTrack.getFile());
//
//        existingTrack.setAlbum(newAlbum);
//        return trackRepo.save(existingTrack);
//    }
//public TrackEntity updateTrack(Long id, TrackEntity updatedTrack) {
//    TrackEntity existingTrack = trackRepo.findById(id)
//            .orElseThrow(() -> new RuntimeException("Track not found"));
//    AlbumEntity newAlbum = albumRepo.findByTrack(updatedTrack)
//            .orElseThrow(() -> new RuntimeException("Album not found"));
//
//    existingTrack.setName(updatedTrack.getName());
//    existingTrack.setAuthor(updatedTrack.getAuthor());
//        existingTrack.setAlbum(newAlbum); // Замените на актуальные свойства трека, которые хотите изменить
//
//    return trackRepo.save(updatedTrack);
//}

    public AlbumEntity changeAlbum(Long id, AlbumEntity updatedAlbum) {
        AlbumEntity existingAlbum = albumRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Альбом не найден: " + id));

        existingAlbum.setName_album(updatedAlbum.getName_album());
        existingAlbum.setPicture(updatedAlbum.getPicture());

        return albumRepo.save(existingAlbum);
    }
}

