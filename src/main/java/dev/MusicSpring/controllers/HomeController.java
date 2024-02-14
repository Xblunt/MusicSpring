package dev.MusicSpring.controllers;


import dev.MusicSpring.db.dto.*;
import dev.MusicSpring.db.entities.auth.AuthUserEntity;
import dev.MusicSpring.db.entities.auth.BaseRole;
import dev.MusicSpring.db.entities.entity.ChatEntity;
import dev.MusicSpring.db.entities.entity.MessageEntity;
import dev.MusicSpring.db.entities.entity.PTEntity;
import dev.MusicSpring.db.entities.entity.TrackEntity;
import dev.MusicSpring.db.repositories.AuthUserRepo;
import dev.MusicSpring.service.AdminService;
import dev.MusicSpring.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("api/client")
public class HomeController {
    @Autowired
    private AuthUserRepo authUserRepo;
    @Autowired
    private HomeService homeService;

//@GetMapping("/users")
//public Page<ShortUser> getAllUsers(
//        @RequestParam(defaultValue = "0") int page,
//        @RequestParam(defaultValue = "2") int size) {
//    PageRequest pageRequest = PageRequest.of(page, size);
//    List<ShortUser> users = StreamSupport.stream(authUserRepo.findAll(pageRequest).spliterator(), false)
//            .filter(el -> !el.getRoles().stream()
//                    .allMatch(role -> !role.getRole().equals(BaseRole.USERS)))
//            .map(el -> new ShortUser(el.getRoleId(), el.getUsername(), el.getName(), el.getSurname()))
//            .collect(Collectors.toList());
//
//
//    return new PageImpl<>(users, pageRequest, authUserRepo.count());
//
//}
//@GetMapping("{user}")
//public Optional<UserDTO> getAuthUser(
////        @PathVariable Long id,
//        Principal user, @RequestParam(defaultValue = "0") int page,
//        @RequestParam(defaultValue = "25") int size) {
//    return homeService.getAuthUser( user,size,page );
//}
//@GetMapping("auth-user")
//public ResponseEntity<UserDTO> getAuthUser(Principal user) {
//    Long authUserId = Long.valueOf(user.getName());
//
//    Optional<UserDTO> userDTO = homeService.getAuthUser(authUserId);
//
//    if (userDTO.isPresent()) {
//        return ResponseEntity.ok(userDTO.get());
//    }
//
//    return ResponseEntity.notFound().build();
//}
//@GetMapping("auth-user")
//public Optional<UserDTO> getAuthUser(Principal user) {
//    if (user != null) {
//        String username = user.getName();
//        return homeService.getAuthUser(username);
//    }
//
//    return Optional.empty();
//}
//@GetMapping("/auth-user")
//@ResponseBody
//public ResponseEntity<UserDTO> getAuthUser(Principal principal) {
//    if (principal != null) {
//        String username = principal.getName();
//        Optional<UserDTO> userDTO = homeService.getAuthUser(username);
//        return userDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    } else {
//        return ResponseEntity.notFound().build();
//    }
//}

//    @GetMapping("auth-user/{username}")
//    public Optional<UserDTO> getauth(
//            @PathVariable("username") String username,
//
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "25") int size) {
//        return homeService.getauth(username, page, size);
//    }


@GetMapping("auth-user")
public List<UserDTO> getAuth(
        @RequestParam("username") String username,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "25") int size) {
    return homeService.getauth(username, page, size);
}

    @GetMapping("playlist")
    public Page<ShortTrack> getPlaylist(
            @RequestParam("username") String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size) {
        return homeService.getPlaylist(username, page, size);
    }
    @PostMapping(value = "/playlist", consumes = "application/json", produces = "application/json")
    public PTEntity like(@RequestBody PTEntity pt, @RequestParam("username") String username, @RequestParam("trackId") Long trackId) {
        return homeService.like(username,trackId, pt);
    }
    @GetMapping("/chats/add")
    public Page<UserDTO> getAllUsers(
            @RequestParam("username") String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size) {
        return homeService.getAllUsers(username, page, size);
    }

    @GetMapping("/chats/{id}")
    public Page<MessageDTO> getChat(
            @PathVariable Long id,

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size) {
        return homeService.getChat(id, page, size);
    }

    @PostMapping("/chats/{id}")
    public MessageEntity createMess( @RequestBody MessageEntity message, @PathVariable Long id,  @RequestParam("username") String username, @RequestParam String messgg) {
        return homeService.createMess(id, message, username,messgg);
    }
    @PostMapping("/chats/{id}/add")
    public MessageEntity createMTrack( @RequestBody MessageEntity message, @PathVariable Long id, @RequestParam("username") String username, @RequestParam Long messgg ) {
        return homeService.createMTrack(id, message,username, messgg);
    }
    @GetMapping("/album")
    public Page<ShortAlbum> getAllAlbums(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size) {
        return homeService.getAllAlbums(page, size);
    }
    @GetMapping("/album/{id}/tracks")
    public Page<ShortTrack> getAllTracksAlbums(@PathVariable Long id,
//                                               Principal user,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "8") int size) {
        return homeService.getAllTracksAlbums(page, size, id);}
    @GetMapping("/tracks/send")
    public Optional<ShortTrack> getAllTracksByTrackId(
            @RequestParam Long trackIds) {
        return homeService.getAllTracksByTrackId(trackIds);
    }

//    @GetMapping("/tracks/mess")
//    public Optional<TrackDTO> getMessTrack(
//            @RequestParam String name,
//
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "25") int size) {
//        return homeService.getMessTrack(name, page, size);
//    }
    @GetMapping("/tracks/mess")
    public Optional<TrackDTO> getMessTrack(
            @RequestParam Long trackId,

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size) {
        return homeService.getMessTrack(trackId, page, size);
    }
    @GetMapping("/tracks")
    public Page<TrackDTO> getAllTracks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size) {
        return homeService.getAllTracks(page, size);
    }
    @GetMapping("/chats")
    public List<ChatDTO> getAllChats(
            @RequestParam("username") String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size) {
        return homeService.getAllChats(username, page, size);
    }
    @PostMapping(value = "/chats", consumes = "application/json", produces = "application/json")
    public ChatEntity createChat( @RequestBody ChatEntity chat, @RequestParam("username") String username, @RequestParam("secondId") Long secondId) {
        return homeService.createChat(username,secondId, chat);
    }
    @PutMapping(value = "/album/{albumId}/tracks/add/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TrackEntity updateTrackAlbum(@PathVariable Long id, @PathVariable Long albumId, @RequestBody TrackDTO trackDto) {

        return homeService.updateTrackPlaylist(id, albumId, trackDto);

    }
}