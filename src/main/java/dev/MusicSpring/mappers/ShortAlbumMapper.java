package dev.MusicSpring.mappers;

import dev.MusicSpring.db.dto.ShortAlbum;
import dev.MusicSpring.db.entities.entity.AlbumEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface ShortAlbumMapper {

    ShortAlbumMapper MAPPER = Mappers.getMapper(ShortAlbumMapper.class);
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name_album", target = "name_album")
    @Mapping(source = "picture", target = "picture")
    ShortAlbum toDto(AlbumEntity album);
}