package dev.MusicSpring.controllers;


import dev.MusicSpring.db.dto.*;
import dev.MusicSpring.db.entities.entity.ChatEntity;
import dev.MusicSpring.db.entities.entity.MessageEntity;
import dev.MusicSpring.db.entities.entity.PTEntity;
import dev.MusicSpring.db.entities.entity.TrackEntity;
import dev.MusicSpring.db.repositories.AuthUserRepo;
import dev.MusicSpring.messages.UpdateSessionMessage;
import dev.MusicSpring.service.HomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/client")
@Slf4j
public class HomeController {
    @Autowired
    private AuthUserRepo authUserRepo;
    @Autowired
    private HomeService homeService;


    @GetMapping("auth-user")
    public List<UserDTO> getAuth(
            @RequestParam("username") String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size)
    {
        return homeService.getauth(username, page, size);
    }


    @GetMapping("playlist")
    public Page<ShortTrack> getPlaylist(
            @RequestParam("username") String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size)
    {
        return homeService.getPlaylist(username, page, size);
    }


    @PostMapping(value = "/playlist", consumes = "application/json", produces = "application/json")
    public PTEntity like(
            @RequestBody PTEntity pt,
            @RequestParam("username") String username,
            @RequestParam("trackId") Long trackId)
    {
        return homeService.like(username,trackId, pt);
    }


    @GetMapping("/chats/add")
    public Page<UserDTO> getAllUsers(
            @RequestParam("username") String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size)
    {
        return homeService.getAllUsers(username, page, size);
    }


    @GetMapping("/chats/{id}")
    public Page<MessageDTO> getChat(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size)
    {
        return homeService.getChat(id, page, size);
    }


//    @MessageMapping("/sinx")
//    @SendTo({"/chats/session/{chatId}"})
//    public SessionDTO updateSession(
//
//            UpdateSessionMessage message)
//    {
//    Boolean action = message.getAction();
//    Double time = message.getTime();
//    Long chatId = message.getChatId();
//    Boolean pause = message.getPause();
//    Double currentTimeOnDevice = message.getCurrentTimeOnDevice();
//    return homeService.updateSession(action, time, chatId, pause, currentTimeOnDevice);
//    }
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/sinx/{chatId}")
    public void updateSession(UpdateSessionMessage message) {
        Boolean action = message.getAction();
        Double time = message.getTime();
        Long chatId = message.getChatId();
        Boolean pause = message.getPause();
        Double currentTimeOnDevice = message.getCurrentTimeOnDevice();

        SessionDTO updatedSession = homeService.updateSession(action, time, chatId, pause, currentTimeOnDevice);

        messagingTemplate.convertAndSend(String.format("/chats/session/%d", chatId), updatedSession);
    }
//    @MessageMapping("/sinx/{chatId}")
//    @SendTo({"/chats/session"})
//    public SessionDTO updateSession(
//            UpdateSessionMessage message)
//    {
//        Boolean action = message.getAction();
//        Double time = message.getTime();
//        Long chatId = message.getChatId();
//        Boolean pause = message.getPause();
//        Double currentTimeOnDevice = message.getCurrentTimeOnDevice();
//        return homeService.updateSession(action, time, chatId, pause,currentTimeOnDevice);
//    }

    @GetMapping("/session")
    public  Optional<SessionDTO> getSession(
            @RequestParam Long chatId)
    {
        return homeService.getSession(chatId);
    }


    @PostMapping("/chats/{id}")
    public MessageEntity createMess(
            @RequestBody MessageEntity message,
            @PathVariable Long id,
            @RequestParam("username") String username,
            @RequestParam String messgg)
    {
        return homeService.createMess(id, message, username,messgg);
    }


    @PostMapping("/chats/{id}/add")
    public MessageEntity createMTrack(
            @RequestBody MessageEntity message,
            @PathVariable Long id,
            @RequestParam("username") String username,
            @RequestParam Long messgg )
    {
        return homeService.createMTrack(id, message,username, messgg);
    }


    @GetMapping("/album")
    public Page<ShortAlbum> getAllAlbums(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size)
    {
        return homeService.getAllAlbums(page, size);
    }


    @GetMapping("/album/{id}/tracks")
    public Page<ShortTrack> getAllTracksAlbums(
            @PathVariable Long id,
//            Principal user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size)
    {
        return homeService.getAllTracksAlbums(page, size, id);
    }


    @GetMapping("/tracks/send")
    public Optional<ShortTrack> getAllTracksByTrackId(
            @RequestParam Long trackIds)
    {
        return homeService.getAllTracksByTrackId(trackIds);
    }


    @GetMapping("/tracks/mess")
    public Optional<TrackDTO> getMessTrack(
            @RequestParam Long trackId,

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size)
    {
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
    public ChatEntity createChat(
            @RequestBody ChatEntity chat,
            @RequestParam("username") String username,
            @RequestParam("secondId") Long secondId)
    {
        return homeService.createChat(username,secondId, chat);
    }


    @PutMapping(value = "/album/{albumId}/tracks/add/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TrackEntity updateTrackAlbum(
            @PathVariable Long id,
            @PathVariable Long albumId,
            @RequestBody TrackDTO trackDto)
    {
        return homeService.updateTrackPlaylist(id, albumId, trackDto);
    }

}