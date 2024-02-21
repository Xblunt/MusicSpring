package dev.MusicSpring.controllers;


import dev.MusicSpring.db.dto.*;
import dev.MusicSpring.db.entities.auth.AuthUserEntity;
import dev.MusicSpring.db.entities.entity.AlbumEntity;
import dev.MusicSpring.db.entities.entity.TrackEntity;
import dev.MusicSpring.db.entities.entity.UserEntity;
import dev.MusicSpring.db.repositories.AlbumRepo;
import dev.MusicSpring.db.repositories.AuthUserRepo;
import dev.MusicSpring.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("api/admin")
public class AdminController {
    @Autowired
    private AuthUserRepo authUserRepo;
    @Autowired
    private AdminService adminService;
    @Autowired
    private AlbumRepo albumRepo;

    @GetMapping("/users")
    public Page<UserDTO> getAllUsers(
//            Principal user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size)
    {
        return adminService.getAllUsers(page, size);
    }

    @GetMapping("/tracks")
    public Page<TrackDTO> getAllTracks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size)
    {
        return adminService.getAllTracks(page, size);
    }
    @GetMapping("/album/{id}/tracks/add")
    public Page<TrackDTO> getAllTracksAdd(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size,
            @PathVariable("id") Long albumIdToExclude)
    {
        return adminService.getAllTracksAdd(page,  size, albumIdToExclude);
    }
    @GetMapping("/tracks/{id}")
    public TrackDTO getAllTracksByTrackId(
            @PathVariable Long id
//            Principal user,
            )
    {
        return adminService.getTrackByTrackId(id);
    }

    @GetMapping("/album")
    public Page<ShortAlbum> getAllAlbums(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size)
    {
        return adminService.getAllAlbums(page, size);
    }
    @GetMapping("/album/{id}/tracks")
    public Page<ShortTrack> getAllTracksAlbums(@PathVariable Long id,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "8") int size)
    {
        return adminService.getAllTracksAlbums(page, size, id);
    }


    @PostMapping(value = "/users", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthUserEntity createUser(
            @RequestBody AuthUserEntity user)
    {
        return adminService.createUser(user);
    }


    @PostMapping(value = "/album/{id}/tracks", consumes = "application/json", produces = "application/json")
    public TrackEntity createTrack(@PathVariable Long id, @RequestBody TrackEntity track) {
        AlbumEntity album = albumRepo.findById(id).orElseThrow(() -> new RuntimeException("Album not found"));
        track.setAlbum(album);
        return adminService.createTrack(id, track);
    }
    @PostMapping(value = "/album",consumes = "application/json", produces = "application/json")
    public AlbumEntity createAlbum(@RequestBody AlbumEntity album) {
        return adminService.createAlbum(album);
    }

    @PutMapping(value = "users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthUserEntity changeUser(@PathVariable("id") Long id, @RequestBody AuthUserEntity updatedUser) {
        return adminService.changeUser(id, updatedUser);
    }

//    @PutMapping(value = "tracks/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public TrackEntity changeTrack( @RequestParam(name = "newAlbumId") Long newAlbumId, @PathVariable Long id, @RequestBody TrackEntity updatedTrack) {
//        AlbumEntity album = albumRepo.findById(id).orElseThrow(() -> new RuntimeException("Album not found"));
//        updatedTrack.setAlbum(album);
//        return adminService.changeTrack(newAlbumId, id, updatedTrack);
//    }
//@PutMapping(value = "tracks/{id}", consumes = "application/json", produces = "application/json")
//public TrackEntity changeTrack(@PathVariable Long id, @RequestBody TrackEntity updatedTrack, @RequestParam("albumId") String albumIdStr) {
//    return adminService.changeTrack(id, updatedTrack, albumIdStr);
//}


    @PutMapping(value = "tracks/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TrackEntity updateTrack(@PathVariable Long id,  @RequestBody TrackDTO trackDto) {

        return adminService.updateTrack(id, trackDto);

    }
    @PutMapping(value = "/album/{albumId}/tracks/add/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TrackEntity updateTrackAlbum(@PathVariable Long id, @PathVariable Long albumId, @RequestBody TrackDTO trackDto) {

        return adminService.updateTrackAlbum(id, albumId, trackDto);

    }
//@PutMapping(value = "tracks/{id}", consumes = "application/json", produces = "application/json")
//public TrackEntity updateTrack(@PathVariable Long id, @RequestBody TrackEntity updatedTrack) {
//    return adminService.updateTrack(id, updatedTrack);
//}


    @PutMapping(value = "album/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AlbumEntity changeAlbum(@PathVariable("id") Long id, @RequestBody AlbumEntity updatedAlbum) {
        return adminService.changeAlbum(id, updatedAlbum);
    }

    @DeleteMapping("users/{id}")
    public Long deleteUser(@PathVariable("id") Long userId) {
        return adminService.deleteUser(userId);
    }

    @DeleteMapping("tracks/{id}")
    public Long deleteTrack(@PathVariable("id") Long trackId) {
        return adminService.deleteTrack(trackId);
    }

    @DeleteMapping("album/{id}/tracks")
    public Long deleteAlbum(@PathVariable("id") Long albumId) {
        return adminService.deleteAlbum(albumId);
    }


}