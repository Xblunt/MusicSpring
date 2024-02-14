package dev.MusicSpring.mappers;

import dev.MusicSpring.db.dto.TrackDTO;
import dev.MusicSpring.db.entities.auth.AuthUserEntity;
import dev.MusicSpring.db.entities.entity.AlbumEntity;
import dev.MusicSpring.db.entities.entity.TrackEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TrackMapper {

    TrackMapper MAPPER = Mappers.getMapper(TrackMapper.class);

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "album", ignore = true)
//    TrackEntity trackDtoToTrack(TrackDTO trackDto);

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "file", ignore = true)
//    @Mapping(target = "album", ignore = true)
//    void updateTrackFromDto(TrackDTO dto, @MappingTarget TrackEntity entity);
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "author", target = "author")
    @Mapping(source = "text", target = "text")
    @Mapping(source = "file", target = "file")
    @Mapping(source = "album", target = "album_id", qualifiedByName = "mapAlbumToId")
    TrackDTO toDto(TrackEntity source);

    @Named("mapAlbumToId")
    default Long mapAlbumToId(AlbumEntity album) {
        return album.getId();
    }
}