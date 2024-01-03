package dev.MusicSpring.controllers;


import dev.MusicSpring.db.dto.*;
import dev.MusicSpring.db.entities.auth.AuthUserEntity;
import dev.MusicSpring.db.entities.entity.AlbumEntity;
import dev.MusicSpring.db.entities.entity.TrackEntity;
import dev.MusicSpring.db.entities.entity.UserEntity;
import dev.MusicSpring.db.repositories.AuthUserRepo;
import dev.MusicSpring.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin")
public class AdminController {
    @Autowired
    private AuthUserRepo authUserRepo;
    @Autowired
    private AdminService adminService;

    @GetMapping("/users")
    public Page<ShortUser> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {
        return adminService.getAllUsers(page, size);
    }

    @GetMapping("/tracks")
    public Page<ShortTrack> getAllTracks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size) {
        return adminService.getAllTracks(page, size);
    }

    @GetMapping("/album")
    public Page<ShortAlbum> getAllAlbums(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size) {
        return adminService.getAllAlbums(page, size);
    }
    @GetMapping("/album/{id}")
    public Page<AlbumDTO> getAllTracksAlbums(@PathVariable Long id,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "8") int size) {
        return adminService.getAllTracksAlbums(page, size, id);}
//@GetMapping("/album/{id}")
//public Page<AlbumDTO> getAllTracksAlbums(@PathVariable int id,
//                                         @RequestParam(defaultValue = "0") int page,
//                                         @RequestParam(defaultValue = "8") int size) {
//    return adminService.getAllTracksAlbums(id, page, size);
//}


    @PostMapping(consumes = "application/json", produces = "application/json")
    public AuthUserEntity createUser(@RequestBody AuthUserEntity user) {
        return adminService.createUser(user);
    }

//    @PostMapping(consumes = "application/json", produces = "application/json")
//    public TrackEntity createTrack(@RequestBody TrackEntity track) {
//        return adminService.createTrack(track);
//    }
//
//    @PostMapping(consumes = "application/json", produces = "application/json")
//    public AlbumEntity createAlbum(@RequestBody AlbumEntity album) {
//        return adminService.createAlbum(album);
//    }

    @PutMapping(value = "users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthUserEntity changeUser(@PathVariable("id") Long id, @RequestBody AuthUserEntity updatedUser) {
        return adminService.changeUser(id, updatedUser);
    }

    @PutMapping(value = "tracks/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TrackEntity changeTrack(@PathVariable("id") Long id, @RequestBody TrackEntity updatedTrack) {
        return adminService.changeTrack(id, updatedTrack);
    }

    @PutMapping(value = "albums/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AlbumEntity changeAlbum(@PathVariable("id") Long id, @RequestBody AlbumEntity updatedAlbum) {
        return adminService.changeAlbum(id, updatedAlbum);
    }

    @DeleteMapping("users/{id}")
    public Long deleteUser(@PathVariable("user_id") Long userId) {
        return adminService.deleteUser(userId);
    }

    @DeleteMapping("tracks/{id}")
    public Long deleteTrack(@PathVariable("track_id") Long trackId) {
        return adminService.deleteTrack(trackId);
    }

    @DeleteMapping("albums/{id}")
    public Long deleteAlbum(@PathVariable("album_id") Long albumId) {
        return adminService.deleteAlbum(albumId);
    }


}