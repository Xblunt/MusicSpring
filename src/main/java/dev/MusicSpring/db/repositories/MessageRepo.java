package dev.MusicSpring.db.repositories;

import dev.MusicSpring.db.entities.entity.MessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MessageRepo extends PagingAndSortingRepository<MessageEntity, Long> {

    Page<MessageEntity> findByChatId(Long chatId, Pageable pageable);
}
