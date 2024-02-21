package dev.MusicSpring.mappers;

import dev.MusicSpring.db.dto.ShortTrack;
import dev.MusicSpring.db.entities.entity.TrackEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface ShortTrackMapper {
    ShortTrackMapper MAPPER = Mappers.getMapper(ShortTrackMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "author", target = "author")
    @Mapping(source = "file", target = "file")
    ShortTrack toDto(TrackEntity track);
}
