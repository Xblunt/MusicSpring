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
import dev.MusicSpring.mappers.ShortAlbumMapper;
import dev.MusicSpring.mappers.ShortTrackMapper;
import dev.MusicSpring.mappers.TrackMapper;
//import dev.MusicSpring.mappers.UserMapper;
import dev.MusicSpring.mappers.UserMapper;
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
    @Autowired
    private PlaylistRepo playlistRepo;




    @Autowired
     private MinioService minioService;
//    public Page<UserDTO> getAllUsers(int page, int size) {
//        PageRequest pageRequest = PageRequest.of(page, size);
//        return authUserRepo.findAll(pageRequest)
//                .map(el -> new UserDTO(el.getRoleId(), el.getFio(), el.getDate()  , el.getText(), el.getPhoto(), el.getUsername()));
//    }
public Page<UserDTO> getAllUsers(int page, int size) {
    PageRequest pageRequest = PageRequest.of(page, size);
    return authUserRepo.findAll(pageRequest)
            .map(UserMapper.MAPPER::toDto);
}

//    public Page<TrackDTO> getAllTracks(int page, int size) {
//        PageRequest pageRequest = PageRequest.of(page, size);
//        return trackRepo.findAll(pageRequest)
//
//                .map(el -> new TrackDTO(el.getId(), el.getName(), el.getAuthor(), el.getText(), el.getFile(),  el.getAlbum().getId()));
//
//
//    }
public Page<TrackDTO> getAllTracks(int page, int size) {
    PageRequest pageRequest = PageRequest.of(page, size);
    return trackRepo.findAll(pageRequest).map(TrackMapper.MAPPER::toDto);
}



//public Page<TrackDTO> getAllTracksAdd(int page, int size, Long albumIdToExclude) {
//
//    Set<TrackEntity> allTracksSet = trackRepo.findAll();
//
//    List<TrackEntity> allTracks = new ArrayList<>(allTracksSet);
//
//    List<TrackEntity> filteredTracks = allTracks.stream()
//            .filter(track -> !track.getAlbum().getId().equals(albumIdToExclude))
//            .collect(Collectors.toList());
//
//    List<TrackEntity> tracksForPage = filteredTracks.subList(page * size, Math.min((page * size) + size, filteredTracks.size()));
//    List<TrackDTO> trackDTOs = tracksForPage.stream()
//            .map(TrackMapper.MAPPER::toDto)
//            .collect(Collectors.toList());
//
//
//    Pageable pageable = PageRequest.of(page, size);
//
//    return new PageImpl<>(trackDTOs, pageable, filteredTracks.size());
//}
public Page<TrackDTO> getAllTracksAdd(int page, int size, Long albumIdToExclude) {
    Pageable pageable = PageRequest.of(page, size);

    Page<TrackEntity> trackPage = trackRepo.findAllByAlbumIdNot(albumIdToExclude, pageable);

    List<TrackDTO> trackDTOs = trackPage.getContent().stream()
            .map(TrackMapper.MAPPER::toDto)
            .collect(Collectors.toList());

    return new PageImpl<>(trackDTOs, pageable, trackPage.getTotalElements());
}


//    public Page<TrackDTO> getAllTracksAdd(int page, int size, Long albumIdToExclude) {
//        Set<TrackEntity> allTracksSet = trackRepo.findAll();
//
//        List<TrackEntity> allTracks = new ArrayList<>(allTracksSet);
//
//        List<TrackEntity> filteredTracks = allTracks.stream()
//                .filter(track -> !track.getAlbum().getId().equals(albumIdToExclude))
//                .collect(Collectors.toList());
//
//        int fromIndex = Math.min(page * size, filteredTracks.size());
//        int toIndex = Math.min((page + 1) * size, filteredTracks.size());
//
//        List<TrackEntity> tracksForPage = filteredTracks.subList(fromIndex, toIndex);
//
//        List<TrackDTO> trackDTOs = tracksForPage.stream()
//                .map(trackMapper::toDto)
//                .collect(Collectors.toList());
//
//        return new PageImpl<>(trackDTOs, PageRequest.of(page, size), filteredTracks.size());
//    }

    public TrackDTO getTrackByTrackId(Long trackId) {


        return TrackMapper.MAPPER.toDto(trackRepo.findById(trackId)
                .orElseThrow(() -> new NoSuchElementException("Track not found with id: " + trackId)));
    }
//public Optional<TrackDTO> getAllTracksByTrackId(Long trackId, int page, int size) {
//    return trackRepo.findById(trackId)
//            .map(trackMapper::toDto);
//}
    public Page<ShortAlbum> getAllAlbums(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return albumRepo.findAll(pageRequest)
                .map(ShortAlbumMapper.MAPPER::toDto);
    }

//    public Page<ShortAlbum> getAllAlbums(int page, int size) {
//        PageRequest pageRequest = PageRequest.of(page, size);
//        return albumRepo.findAll(pageRequest)
//                .map(shortAlbumMapper::toDto);
//    }

    @Transactional
    public Page<ShortTrack> getAllTracksAlbums(int page, int size, Long id) {
        AlbumEntity album = albumRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Альбом не найден: " + id));

        List<ShortTrack> trackList = album.getTracks().stream()
                .map(ShortTrackMapper.MAPPER::toDto)
                .collect(Collectors.toList());

    List<ShortTrack> pagedTrackList = trackList.subList(page * size, Math.min((page * size) + size, trackList.size()));
    return new PageImpl<>(pagedTrackList, PageRequest.of(page, size), trackList.size());
}

//    public Page<ShortTrack> getAllTracksAlbums(int page, int size, Long id) {
//        AlbumEntity album = albumRepo.findById(id)
//                .orElseThrow(() -> new RuntimeException("Альбом не найден: " + id));
//
//        Pageable pageable = PageRequest.of(page, size);
//        Page<TrackEntity> trackPage = trackRepo.findAllByAlbum(album, pageable);
//
//        List<ShortTrack> pagedTrackList = trackPage.getContent().stream()
//                .map(shortTrackMapper::toDto)
//                .collect(Collectors.toList());
//
//        return new PageImpl<>(pagedTrackList, pageable, trackPage.getTotalElements());
//    }

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
        String contentType = "image/jpeg";


        String trackUrl = minioService.saveImgToMinio(user.getPhoto(), user.getUsername(),null, contentType );
        user.setPhoto(trackUrl);
        return authUserRepo.save(user);
    }
//public AuthUserEntity createUser(UserDTO userDto) {
//    AuthUserEntity user = userMapper.toEntity(userDto);
//    String contentType = "image/jpeg";
//    String trackUrl = minioService.saveImgToMinio(user.getPhoto(), user.getUsername(), null, contentType);
//    user.setPhoto(trackUrl);
//    return authUserRepo.save(user);
//}

    ///11111111111111111111
//public TrackEntity createTrack(Long id, TrackEntity track) {
//    AlbumEntity album = albumRepo.findById(id).orElseThrow(() -> new RuntimeException("Album not found"));
//    track.setAlbum(album);
//    return trackRepo.save(track);
//}
    @Transactional
public TrackEntity createTrack(Long id, TrackEntity track) {
    AlbumEntity album = albumRepo.findById(id).orElseThrow(() -> new RuntimeException("Album not found"));
    track.setAlbum(album);

    String contentType = "audio/mp3";


    String trackUrl = minioService.saveTrackToMinio(track.getFile(), track.getName(), null, contentType );
    track.setFile(trackUrl);

    return trackRepo.save(track);
}
    public AlbumEntity createAlbum(AlbumEntity album) {

        String contentType = "image/jpeg";


        String trackUrl = minioService.saveImgToMinio(album.getPicture(), album.getName_album(),null, contentType );
        album.setPicture(trackUrl);

        return albumRepo.save(album);
    }
    public AuthUserEntity changeUser(Long id, AuthUserEntity updatedUser) {
        AuthUserEntity existingUser = authUserRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден: " + id));
        minioService.deleteFile(existingUser.getUsername());
//        existingUser = UserMapper.MAPPER.updateAuthUserEntity(existingUser, updatedUser);
        existingUser.setFio(updatedUser.getFio());
        existingUser.setText(updatedUser.getText());
        existingUser.setDate(updatedUser.getDate());
//        existingUser.setPhoto(updatedUser.getPhoto());
        String contentType = "image/jpeg";
        String userUrl = minioService.updateImgMinio(updatedUser.getPhoto(),updatedUser.getUsername(), contentType );
        existingUser.setPhoto(userUrl);
        return authUserRepo.save(existingUser);
    }
    @Transactional
    public TrackEntity updateTrack(Long id, TrackDTO trackDto) {
        TrackEntity track = trackRepo.findById(id)
               .orElseThrow(() -> new RuntimeException("Track not found: " + id));
        minioService.deleteTrackFile(track.getName());
        track.setName(trackDto.getName());
        track.setAuthor(trackDto.getAuthor());
        track.setText(trackDto.getText());


        String contentType = "audio/mp3";


        String trackUrl = minioService.saveTrackToMinio(trackDto.getFile(),trackDto.getName(), null, contentType );
        track.setFile(trackUrl);
        Long newAlbumId = trackDto.getAlbum_id();

        AlbumEntity newAlbum = albumRepo.findById(newAlbumId)
                .orElseThrow(() -> new RuntimeException("Album not found: " + newAlbumId));

        track.setAlbum(newAlbum);

        return trackRepo.save(track);
    }
//public TrackDTO updateTrack(Long id, TrackDTO trackDto) {
//    TrackEntity track = trackRepo.findById(id)
//            .orElseThrow(() -> new RuntimeException("Track not found: " + id));
//
//
//    minioService.deleteTrackFile(track.getName());
//
//
//    TrackMapper.MAPPER.updateTrackFromDto(trackDto, track);
//
//    String contentType = "audio/mp3";
//
//
//    String trackUrl = minioService.saveTrackToMinio(trackDto.getFile(), trackDto.getName(), null, contentType);
//    track.setFile(trackUrl);
//
//
//    TrackEntity updatedTrack = trackRepo.save(track);
//
//
//    return TrackMapper.MAPPER.toDto(updatedTrack);
//}

   @Transactional
    public TrackEntity updateTrackAlbum(Long id, Long albumId, TrackDTO trackDto) {
        TrackEntity track = trackRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Track not found: " + id));

        track.setName(trackDto.getName());
        track.setAuthor(trackDto.getAuthor());
        track.setText(trackDto.getText());
        track.setFile(trackDto.getFile());

        Long newAlbumId = trackDto.getAlbum_id();
        AlbumEntity newAlbum = albumRepo.findById(newAlbumId)
                .orElseThrow(() -> new RuntimeException("Album not found: " + newAlbumId));


        track.setAlbum(newAlbum);

        return trackRepo.save(track);
    }

    public AlbumEntity changeAlbum(Long id, AlbumEntity updatedAlbum) {
        AlbumEntity existingAlbum = albumRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Альбом не найден: " + id));
        minioService.deleteFile(existingAlbum.getName_album());

        existingAlbum.setName_album(updatedAlbum.getName_album());
//        existingAlbum.setPicture(updatedAlbum.getPicture());
        String contentType = "image/jpeg";
        String userUrl = minioService.saveImgToMinio(updatedAlbum.getPicture(),updatedAlbum.getName_album(), null, contentType );
        existingAlbum.setPicture(userUrl);

        return albumRepo.save(existingAlbum);
    }
}

