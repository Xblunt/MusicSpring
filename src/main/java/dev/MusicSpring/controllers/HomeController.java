package dev.MusicSpring.controllers;


import dev.MusicSpring.db.dto.ChatDTO;
import dev.MusicSpring.db.dto.ShortUser;
import dev.MusicSpring.db.entities.auth.BaseRole;
import dev.MusicSpring.db.entities.entity.ChatEntity;
import dev.MusicSpring.db.entities.entity.MessageEntity;
import dev.MusicSpring.db.repositories.AuthUserRepo;
import dev.MusicSpring.service.AdminService;
import dev.MusicSpring.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("api/home")
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

    @GetMapping("/{chatId}")
    public ResponseEntity<ChatEntity> getChatById(@PathVariable Long chatId) {
        ChatEntity chat = homeService.getChatById(chatId);
        if (chat != null) {
            return ResponseEntity.ok(chat);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{messageId}")
    public ResponseEntity<MessageEntity> getMessageById(@PathVariable Long messageId) {
        MessageEntity message = homeService.getMessageById(messageId);
        if (message != null) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}