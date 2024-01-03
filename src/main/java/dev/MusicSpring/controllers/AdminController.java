package dev.MusicSpring.controllers;


import dev.MusicSpring.db.dto.ShortUser;
import dev.MusicSpring.db.dto.TrackDTO;
import dev.MusicSpring.db.dto.UserDTO;
import dev.MusicSpring.db.entities.auth.AuthUserEntity;
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
    public Page<UserDTO> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {
        return adminService.getAllUsers(page, size);
    }

    @GetMapping("/tracks")
        public Page<TrackDTO> getAllTracks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {
        return adminService.getAllTracks(page, size);
    }


    @PostMapping(consumes = "application/json", produces = "application/json")
    public UserEntity createUser(@RequestBody UserEntity user) {
        return adminService.createUser(user);
    }
    @PostMapping(consumes = "application/json", produces = "application/json")
    public TrackEntity createTrack(@RequestBody TrackEntity track) {
        return adminService.createTrack(track);
    }

    @PutMapping(value = "users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserEntity changeUser(@PathVariable("id") Long id, @RequestBody UserEntity updatedUser) {
        return adminService.changeUser(id, updatedUser);
    }
    @PutMapping(value = "tracks/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TrackEntity changeTrack(@PathVariable("id") Long id, @RequestBody TrackEntity updatedTrack) {
        return adminService.changeTrack(id, updatedTrack);
    }

    @DeleteMapping("users/{id}")
    public Long deleteUser(@PathVariable("user_id") Long userId) {
        return adminService.deleteUser(userId);
    }

    @DeleteMapping("tracks/{id}")
    public Long deleteTrack(@PathVariable("track_id") Long trackId) {
        return adminService.deleteTrack(trackId);
    }
}