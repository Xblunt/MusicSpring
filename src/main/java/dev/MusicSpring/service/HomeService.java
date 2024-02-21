package dev.MusicSpring.service;

import dev.MusicSpring.db.dto.*;
import dev.MusicSpring.db.entities.auth.AuthUserEntity;
import dev.MusicSpring.db.entities.auth.RoleUserEntity;
import dev.MusicSpring.db.entities.entity.*;
import dev.MusicSpring.db.repositories.*;
import dev.MusicSpring.mappers.*;
import javax.validation.Valid;
import net.bytebuddy.implementation.bytecode.ShiftLeft;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class HomeService {
    @Autowired
    private ChatRepo chatRepo;
    @Autowired
    private MessageRepo messageRepo;
    @Autowired
    private AuthUserRepo authUserRepo;
    @Autowired
    private TrackRepo trackRepo;
    @Autowired
    private AlbumRepo albumRepo;
    @Autowired
    private PlaylistRepo playlistRepo;
    @Autowired
    private PlaytRepo playtRepo;
    @Autowired
    private SessionRepo sessionRepo;




    public Page<TrackDTO> getAllTracks(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return trackRepo.findAll(pageRequest)

                .map(TrackMapper.MAPPER::toDto);
    }
//public Page<TrackDTO> getAllTracks(int page, int size) {
//    PageRequest pageRequest = PageRequest.of(page, size);
//    return trackRepo.findAll(pageRequest)
//            .map(trackMapper::toDto);
//}

    public List<UserDTO> getauth(String username, int page, int size) {
//        PageRequest pageRequest = PageRequest.of(page, size);
        List<AuthUserEntity> chatEntities = authUserRepo.findByUsername(username);
                return chatEntities.stream()
                .map(UserMapper.MAPPER::toDto)
                 .collect(Collectors.toList());
    }

//    public List<UserDTO> getauth(String username, int page, int size) {
//        List<AuthUserEntity> authUserEntities = authUserRepo.findByUsername(username);
//        return authUserEntities.stream()
//                .map(userMapper::toDto)
//                .collect(Collectors.toList());
//    }
    @Transactional
    public Page<ShortTrack> getPlaylist(String username, int page, int size) {
     Optional<AuthUserEntity> userOptional = authUserRepo.findByUsernameIgnoreCase(username);
       AuthUserEntity user = userOptional.orElseThrow(() -> new RuntimeException("User not found"));
    Long userId = user.getId();

    Optional<PlaylistEntity> playlistOptional = playlistRepo.findById(userId);
    PlaylistEntity playlist = playlistOptional.orElseThrow(() -> new RuntimeException("Playlist not found"));
    Long playlistId = playlist.getId();

    PageRequest pageRequest = PageRequest.of(page, size);
    List<PTEntity> ptList = playtRepo.findByPlaylistId(playlistId);

    List<ShortTrack> trackList = ptList.stream()
            .map(ptEntity -> {
                TrackEntity track = ptEntity.getTrack();
                return new ShortTrack(track.getId(), track.getName(), track.getAuthor(), track.getFile());
            })
            .collect(Collectors.toList());

    int start = (int) pageRequest.getOffset();
    int end = Math.min((start + pageRequest.getPageSize()), trackList.size());
    Page<ShortTrack> trackPage = new PageImpl<>(trackList.subList(start, end), pageRequest, trackList.size());

    return trackPage;
}
//public Page<ShortTrack> getPlaylist(String username, int page, int size) {
//    Optional<AuthUserEntity> userOptional = authUserRepo.findByUsernameIgnoreCase(username);
//    AuthUserEntity user = userOptional.orElseThrow(() -> new RuntimeException("User not found"));
//    Long userId = user.getId();
//
//    Optional<PlaylistEntity> playlistOptional = playlistRepo.findById(userId);
//    PlaylistEntity playlist = playlistOptional.orElseThrow(() -> new RuntimeException("Playlist not found"));
//    Long playlistId = playlist.getId();
//
//    PageRequest pageRequest = PageRequest.of(page, size);
//    List<PTEntity> ptList = playtRepo.findByPlaylistId(playlistId);
//
//    List<ShortTrack> trackList = ptList.stream()
//            .map(shortTrackMapper::toShortTrack)
//            .collect(Collectors.toList());
//
//    int start = (int) pageRequest.getOffset();
//    int end = Math.min((start + pageRequest.getPageSize()), trackList.size());
//    Page<ShortTrack> trackPage = new PageImpl<>(trackList.subList(start, end), pageRequest, trackList.size());
//
//    return trackPage;
//}
    @Transactional
    public PTEntity like(String username,Long trackId, PTEntity pt) {

        Optional<AuthUserEntity> userOptional = authUserRepo.findByUsernameIgnoreCase(username);
        AuthUserEntity user = userOptional.get();
        Long usid = user.getId();
      PlaylistEntity playlistID = playlistRepo.findById(usid).orElseThrow(() -> new RuntimeException("Album not found"));
       pt.setPlaylist(playlistID);


        TrackEntity idUs2 = trackRepo.findById(trackId).orElseThrow(() -> new RuntimeException("Album not found"));
        pt.setTrack(idUs2);


        return playtRepo.save(pt);

    }
    @Transactional
    public List<ChatDTO> getAllChats(String username, int page, int size) {
        Optional<AuthUserEntity> userOptional = authUserRepo.findByUsernameIgnoreCase(username);

        AuthUserEntity user = userOptional.get();
        Long first_id = user.getId();
        Long second_id = user.getId();

        List<ChatEntity> chatEntities1 = chatRepo.findByFirstUserId(first_id);
        List<ChatEntity> chatEntities2 = chatRepo.findBySecondUserId(second_id);

        Stream<ChatEntity> chatsStream = Stream.concat(chatEntities1.stream(), chatEntities2.stream());

        return chatsStream
                .map(ChatMapper.MAPPER::toDto)
                .collect(Collectors.toList());
    }
//public List<ChatDTO> getAllChats(String username, int page, int size) {
//    Optional<AuthUserEntity> userOptional = authUserRepo.findByUsernameIgnoreCase(username);
//    AuthUserEntity user = userOptional.get();
//    Long firstUserId = user.getId();
//    Long secondUserId = user.getId();
//
//    List<ChatEntity> chatEntities1 = chatRepo.findByFirstUserId(firstUserId);
//    List<ChatEntity> chatEntities2 = chatRepo.findBySecondUserId(secondUserId);
//
//    Stream<ChatEntity> chatsStream = Stream.concat(chatEntities1.stream(), chatEntities2.stream());
//
//    return chatsStream
//            .map(chatMapper::toDto)
//            .collect(Collectors.toList());
//}
    @Transactional
    public Page<MessageDTO> getChat(Long id, int page, int size) {
        ChatEntity chat = chatRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Чат не найден: " + id));

        Page<MessageEntity> messagePage = messageRepo.findByChatId(chat.getId(),
                PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id")));

        List<MessageDTO> messageList = messagePage.getContent().stream()
                .map(mess -> {
                    Long trackId = mess.getTrack() != null ? mess.getTrack().getId() : null;
                    return new MessageDTO(
                            mess.getText_mess(),
                            mess.getSecond().getId(),
                            mess.getFirst().getId(),
                            trackId
                    );
                })
                .collect(Collectors.toList());

        return new PageImpl<>(messageList, messagePage.getPageable(), messagePage.getTotalElements());
    }

//    public Page<MessageDTO> getChat(Long id, int page, int size) {
//        ChatEntity chat = chatRepo.findById(id)
//                .orElseThrow(() -> new RuntimeException("Чат не найден: " + id));
//
//        Page<MessageEntity> messagePage = messageRepo.findByChatId(chat.getId(), PageRequest.of(page, size));
//
//        List<MessageDTO> messageList = messagePage.getContent().stream()
//                .map(messageMapper::toDto)
//                .collect(Collectors.toList());
//
//        return new PageImpl<>(messageList, messagePage.getPageable(), messagePage.getTotalElements());
//    }
//public Page<MessageDTO> getChat(Long id, int page, int size) {
//    ChatEntity chat = chatRepo.findById(id)
//            .orElseThrow(() -> new RuntimeException("Чат не найден: " + id));
//
//    List<MessageDTO> messageList = chat.getMessage().stream()
//            .map(mess -> {
//                MessageDTO messageDTO = new MessageDTO(mess.getText_mess(), mess.getSecond().getId(), mess.getFirst().getId(), mess.getTrack().getId());
//                if (mess.getTrack().getId() != null && mess.getTrack().getId() != 0) {
//                   Optional<ShortTrack> trackDTO = getAllTracksByTrackId(mess.getTrack().getId());
//                    messageDTO.setTrack_id(trackDTO);
//                }
//                return messageDTO;
//            })
//            .collect(Collectors.toList());
//
//    List<MessageDTO> pagedMessageList = messageList.subList(page * size, Math.min((page * size) + size, messageList.size()));
//    return new PageImpl<>(pagedMessageList, PageRequest.of(page, size), messageList.size());
//}
    @Transactional
    public MessageEntity createMTrack(Long id, MessageEntity message, String username, Long messgg) {
        ChatEntity chat = chatRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Альбом не найден: " + id));
        AuthUserEntity secondId = chat.getSecondUser();

       AuthUserEntity user = authUserRepo.findByUsernameIgnoreCase(username)
               .orElseThrow(() -> new RuntimeException("Альбом не найден: " + username));

        TrackEntity track = trackRepo.findById(messgg)
                .orElseThrow(() -> new RuntimeException("Альбом не найден: " + messgg));

        message.setTrack(track);
        message.setChat(chat);
        message.setText_mess(null);
       message.setSecond(secondId);
       message.setFirst(user);

        track.setMessage(message);
        return messageRepo.save(message);
    }
    public MessageEntity createMess(Long id, MessageEntity message, String username, String messgg) {
        ChatEntity chat = chatRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Чат не найден: " + id));
        AuthUserEntity secondId = chat.getSecondUser();
        AuthUserEntity user = authUserRepo.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден: " + username));


        if (secondId.equals(user)) {
            AuthUserEntity notSecond = chat.getFirstUser();
            message.setSecond(notSecond);
        } else {
            message.setSecond(secondId);
        }

        message.setTrack(null);
        message.setChat(chat);
        message.setText_mess(messgg);
        message.setFirst(user);

        return messageRepo.save(message);
    }
    public Page<UserDTO> getAllUsers(String username, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return authUserRepo.findByUsernameNot(username, pageRequest)
                .map(UserMapper.MAPPER::toDto);
    }
//public Page<UserDTO> getAllUsers(String username, int page, int size) {
//    PageRequest pageRequest = PageRequest.of(page, size);
//    return authUserRepo.findByUsernameNot(username, pageRequest)
//            .map(userMapper::toDto);
//}
    @Transactional
    public ChatEntity createChat(String username,Long secondId, ChatEntity chat) {

        SessionEntity session = new SessionEntity();
        session.setAction(null);
        session.setTime(null);
        session = sessionRepo.save(session);

        Optional<AuthUserEntity> userOptional = authUserRepo.findByUsernameIgnoreCase(username);
        AuthUserEntity user = userOptional.get();
        Long first_id = user.getId();
        AuthUserEntity idUs = authUserRepo.findById(first_id).orElseThrow(() -> new RuntimeException("Album not found"));
            chat.setFirstUser(idUs);


        AuthUserEntity idUs2 = authUserRepo.findById(secondId).orElseThrow(() -> new RuntimeException("Album not found"));
        chat.setSecondUser(idUs2);


        chat.setSessionEntity(session);

        return chatRepo.save(chat);
    }
    public Page<ShortAlbum> getAllAlbums(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return albumRepo.findAll(pageRequest)
                .map(ShortAlbumMapper.MAPPER::toDto);
    }
public Optional<SessionDTO> getSession(Long chatId){
    return sessionRepo.findById(chatId)
            .map(el -> new SessionDTO( el.getId(),el.getTime(),el.getAction(), el.getPause()));


}
    public SessionDTO updateSession(String action, Double time, Long chatId, Boolean pause) {
        ChatEntity chat = chatRepo.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Чат не найден: " + chatId));

        Long sessionid = chat.getSessionEntity().getId();
        SessionEntity session = sessionRepo.findById(sessionid)
                .orElseThrow(() -> new RuntimeException("Чат не найден: " + sessionid));


        session.setAction(action);
        session.setTime(time);
        session.setPause(pause);

        session = sessionRepo.save(session);


        return new SessionDTO(session.getId(), session.getTime(), session.getAction(), session.getPause());
    }
//    public Page<ShortAlbum> getAllAlbums(int page, int size) {
//        PageRequest pageRequest = PageRequest.of(page, size);
//        return albumRepo.findAll(pageRequest)
//                .map(shortAlbumMapper::toDto);
//    }
//    public List<ShortTrack> getAllTracksByTrackId(List<Long> trackIds) {
//
//        List<ShortTrack> result = new ArrayList<>();
//        for (Long trackId : trackIds) {
//            Optional<TrackEntity> trackOptional = trackRepo.findById(trackId);
//            trackOptional.ifPresent(track -> result.add(new ShortTrack(track.getId(), track.getName(), track.getAuthor(), track.getFile())));
//        }
//        return result;
//    }
//public List<ShortTrack> getAllTracksByTrackId(List<Long> trackIds) {
//    List<ShortTrack> shortTracks = new ArrayList<>();
//
//    if (!trackIds.isEmpty()) {
//        Long lastTrackId = trackIds.get(trackIds.size() - 1);
//        Optional<TrackEntity> lastTrackOptional = trackRepo.findById(lastTrackId);
//
//        lastTrackOptional.ifPresent(lastTrack ->
//                shortTracks.add(new ShortTrack(lastTrack.getId(), lastTrack.getName(), lastTrack.getAuthor(), lastTrack.getFile()))
//        );
//    }
//
//    return shortTracks;
//}
public Optional<ShortTrack> getAllTracksByTrackId(Long trackId) {

    return trackRepo.findById(trackId)
            .map(ShortTrackMapper.MAPPER::toDto);

}
//    public ShortTrack[] getAllTracksByTrackId(Long trackId) {
//        return trackRepo.findById(trackId)
//                .map(el -> new ShortTrack[]{new ShortTrack(el.getId(), el.getName(), el.getAuthor(), el.getFile())})
//                .orElse(new ShortTrack[0]);
//    }

//    public List<ShortTrack> getAllTracksByTrackId(List<Long> trackIds) {
//        List<ShortTrack> result = new ArrayList<>();
//        for (Long trackId : trackIds) {
//            trackRepo.findById(trackId)
//                    .map(shortTrackMapper::toDto)
//                    .ifPresent(result::add);
//        }
//        return result;
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
//public Page<ShortTrack> getAllTracksAlbums(int page, int size, Long id) {
//    AlbumEntity album = albumRepo.findById(id)
//            .orElseThrow(() -> new RuntimeException("Альбом не найден: " + id));
//
//    List<ShortTrack> trackList = album.getTracks().stream()
//            .map(shortTrackMapper::toDto)
//            .collect(Collectors.toList());
//
//    int startIndex = page * size;
//    int endIndex = Math.min(startIndex + size, trackList.size());
//    List<ShortTrack> pagedTrackList = trackList.subList(startIndex, endIndex);
//
//    return new PageImpl<>(pagedTrackList, PageRequest.of(page, size), trackList.size());
//}
    @Transactional
    public TrackEntity updateTrackPlaylist(Long id, Long albumId, TrackDTO trackDto) {
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
//    public Optional<TrackDTO> getMessTrack(String name,int page, int size){
////
////        TrackEntity newAlbum = trackRepo.findByname(name)
////                .orElseThrow(() -> new RuntimeException("Album not found: " + name));
////        Long idt = newAlbum.getId();
//       return trackRepo.findByname(name)
//               .map(el -> new TrackDTO(el.getId(), el.getName(), el.getAuthor(), el.getText(), el.getFile(),  el.getAlbum().getId()));
//
//    }
    public Optional<TrackDTO> getMessTrack(Long trackId,int page, int size){
//
//        TrackEntity newAlbum = trackRepo.findByname(name)
//                .orElseThrow(() -> new RuntimeException("Album not found: " + name));
//        Long idt = newAlbum.getId();
        return trackRepo.findById(trackId)
                .map(TrackMapper.MAPPER::toDto);

    }
//public Optional<TrackDTO> getMessTrack(Long trackId, int page, int size) {
//    return trackRepo.findById(trackId)
//            .map(trackMapper::toDto);
//}
}
