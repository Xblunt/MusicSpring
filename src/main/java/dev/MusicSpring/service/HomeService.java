package dev.MusicSpring.service;

import dev.MusicSpring.db.dto.*;
import dev.MusicSpring.db.entities.auth.AuthUserEntity;
import dev.MusicSpring.db.entities.auth.RoleUserEntity;
import dev.MusicSpring.db.entities.entity.*;
import dev.MusicSpring.db.repositories.*;
import net.bytebuddy.implementation.bytecode.ShiftLeft;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.security.Principal;
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





    public Page<TrackDTO> getAllTracks(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return trackRepo.findAll(pageRequest)

                .map(el -> new TrackDTO(el.getId(), el.getName(), el.getAuthor(), el.getText(), el.getFile(),  el.getAlbum().getId()));
    }
    public List<UserDTO> getauth(String username, int page, int size) {
//        PageRequest pageRequest = PageRequest.of(page, size);
        List<AuthUserEntity> chatEntities = authUserRepo.findByUsername(username);
                return chatEntities.stream()
                .map(el -> new UserDTO(el.getRoleId(), el.getFio(), el.getDate() , el.getText(), el.getPhoto(), el.getUsername()))
                 .collect(Collectors.toList());
    }

//    public Page<ShortTrack> getPlaylist(String username, int page, int size) {
//
//        Optional<AuthUserEntity> userOptional = authUserRepo.findByUsernameIgnoreCase(username);
//        AuthUserEntity user = userOptional.get();
//        Long id = user.getId();
//        Optional<PlaylistEntity> playid = playlistRepo.findById(id);
//        PlaylistEntity playlist = playid.get();
//        Long idp = playlist.getId();
//        Optional<PTEntity> pt = playtRepo.findByidd(idp);
//        PTEntity ptEntity = pt.get();
//        Long trackIds = ptEntity.getTrack().getId();
//
//
//
//    }
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
//    Page<PTEntity> ptPage = playtRepo.findByid(playlistId, pageRequest);
//
//    List<ShortTrack> trackList = ptPage.getContent().stream()
//            .map(ptEntity -> {
//                TrackEntity track = ptEntity.getTrack();
//                return new ShortTrack(track.getId(), track.getName(), track.getAuthor());
//            })
//            .collect(Collectors.toList());
//
//    return new PageImpl<>(trackList, pageRequest, ptPage.getTotalElements());
//}
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
                return new ShortTrack(track.getId(), track.getName(), track.getAuthor());
            })
            .collect(Collectors.toList());

    int start = (int) pageRequest.getOffset();
    int end = Math.min((start + pageRequest.getPageSize()), trackList.size());
    Page<ShortTrack> trackPage = new PageImpl<>(trackList.subList(start, end), pageRequest, trackList.size());

    return trackPage;
}

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

    public List<ChatDTO> getAllChats(String username, int page, int size) {
        Optional<AuthUserEntity> userOptional = authUserRepo.findByUsernameIgnoreCase(username);

        AuthUserEntity user = userOptional.get();
        Long first_id = user.getId();
        Long second_id = user.getId();

        List<ChatEntity> chatEntities1 = chatRepo.findByFirstUserId(first_id);
        List<ChatEntity> chatEntities2 = chatRepo.findBySecondUserId(second_id);

        Stream<ChatEntity> chatsStream = Stream.concat(chatEntities1.stream(), chatEntities2.stream());

        return chatsStream
                .map(el -> new ChatDTO(el.getId(), el.getFirstUser().getId(), el.getSecondUser().getId(), el.getChatname()))
                .collect(Collectors.toList());
    }


    public Page<MessageDTO> getChat(Long id, int page, int size) {
        ChatEntity chat = chatRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Альбом не найден: " + id));


        List<MessageDTO> massegaList = chat.getMessage().stream()
                .map(mess -> new MessageDTO(mess.getText_mess(), mess.getSecond().getId(),mess.getFirst().getId()))
                .collect(Collectors.toList());
        List<MessageDTO> pagedTrackList = massegaList.subList(page * size, Math.min((page * size) + size, massegaList.size()));
        return new PageImpl<>(pagedTrackList, PageRequest.of(page, size), massegaList.size());

    }

    public MessageEntity createMTrack(Long id, MessageEntity message, String username, Long messgg ) {
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


        return messageRepo.save(message);
    }
    public MessageEntity createMess(Long id, MessageEntity message, String username, String messgg ) {
        ChatEntity chat = chatRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Альбом не найден: " + id));
        AuthUserEntity secondId = chat.getSecondUser();
        AuthUserEntity user = authUserRepo.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new RuntimeException("Альбом не найден: " + username));
        message.setTrack(null);
        message.setChat(chat);
        message.setText_mess(messgg);
        message.setSecond(secondId);
        message.setFirst(user);


        return messageRepo.save(message);
    }
    public Page<UserDTO> getAllUsers(String username, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return authUserRepo.findByUsernameNot(username, pageRequest)
                .map(el -> new UserDTO(el.getRoleId(), el.getFio(), el.getPhoto() , el.getDate(), el.getText(), el.getUsername()));
    }

    public ChatEntity createChat(String username,Long secondId, ChatEntity chat) {

        Optional<AuthUserEntity> userOptional = authUserRepo.findByUsernameIgnoreCase(username);
        AuthUserEntity user = userOptional.get();
        Long first_id = user.getId();
        AuthUserEntity idUs = authUserRepo.findById(first_id).orElseThrow(() -> new RuntimeException("Album not found"));
            chat.setFirstUser(idUs);


        AuthUserEntity idUs2 = authUserRepo.findById(secondId).orElseThrow(() -> new RuntimeException("Album not found"));
        chat.setSecondUser(idUs2);


        return chatRepo.save(chat);

    }
    public Page<ShortAlbum> getAllAlbums(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return albumRepo.findAll(pageRequest)
                .map(el -> new ShortAlbum(el.getId(), el.getName_album(), el.getPicture()));
    }
    public Optional<TrackDTO> getAllTracksByTrackId(Long trackId,  int page, int size) {

        return trackRepo.findById(trackId)
                .map(el -> new TrackDTO(el.getId(), el.getName(), el.getAuthor(), el.getText(), el.getFile(),  el.getAlbum().getId()));
    }
    public Page<ShortTrack> getAllTracksAlbums(int page, int size, Long id) {
        AlbumEntity album = albumRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Альбом не найден: " + id));


        List<ShortTrack> trackList = album.getTracks().stream()
                .map(track -> new ShortTrack(track.getId(), track.getName(), track.getAuthor()))
                .collect(Collectors.toList());

        List<ShortTrack> pagedTrackList = trackList.subList(page * size, Math.min((page * size) + size, trackList.size()));
        return new PageImpl<>(pagedTrackList, PageRequest.of(page, size), trackList.size());
    }
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
}
