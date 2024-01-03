package dev.MusicSpring.service;

import dev.MusicSpring.db.dto.ChatDTO;
import dev.MusicSpring.db.entities.entity.ChatEntity;
import dev.MusicSpring.db.entities.entity.MessageEntity;
import dev.MusicSpring.db.repositories.ChatRepo;
import dev.MusicSpring.db.repositories.MessageRepo;
import dev.MusicSpring.db.repositories.TrackRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HomeService {
    @Autowired
    private ChatRepo chatRepo;
    @Autowired
    private MessageRepo messageRepo;
    public ChatEntity getChatById(Long chatId) {
        Optional<ChatEntity> optionalChat = chatRepo.findById(chatId);
        return optionalChat.orElse(null);
    }
    public MessageEntity getMessageById(Long messageId) {
        Optional<MessageEntity> optionalMessage = messageRepo.findById(messageId);
        return optionalMessage.orElse(null);
    }
}
