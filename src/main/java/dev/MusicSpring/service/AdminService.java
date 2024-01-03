package dev.MusicSpring.service;

import dev.MusicSpring.db.dto.TrackDTO;
import dev.MusicSpring.db.dto.UserDTO;
import dev.MusicSpring.db.entities.entity.TrackEntity;
import dev.MusicSpring.db.entities.entity.UserEntity;
import dev.MusicSpring.db.repositories.AuthUserRepo;
import dev.MusicSpring.db.repositories.TrackRepo;
import dev.MusicSpring.db.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private TrackRepo trackRepo;


    public Page<UserDTO> getAllUsers(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return userRepo.findAll(pageRequest)
                .map(el -> new UserDTO(el.getId(), el.getFio()));
    }

    public Page<TrackDTO> getAllTracks(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return trackRepo.findAll(pageRequest)
                .map(el -> new TrackDTO(el.getId(), el.getName(), el.getAuthor()));
    }

    public Long deleteUser(Long userId) {
        userRepo.deleteById(userId);
        return userId;
    }
    public Long deleteTrack(Long trackId) {
        userRepo.deleteById(trackId);
        return trackId;
    }
    public UserEntity createUser(UserEntity user) {
        return userRepo.save(user);
    }
    public TrackEntity createTrack(TrackEntity track) {
        return trackRepo.save(track);
    }

    public UserEntity changeUser(Long id, UserEntity updatedUser) {
        UserEntity existingUser = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден: " + id));

        existingUser.setFio(updatedUser.getFio());

        return userRepo.save(existingUser);
    }
    public TrackEntity changeTrack(Long id, TrackEntity updatedTrack) {
        TrackEntity existingTrack = trackRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден: " + id));

        existingTrack.setName(updatedTrack.getName());
        existingTrack.setAuthor(updatedTrack.getAuthor());

        return trackRepo.save(existingTrack);
    }
}
