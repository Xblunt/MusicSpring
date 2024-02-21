package dev.MusicSpring.mappers;

import dev.MusicSpring.db.dto.TrackDTO;
import dev.MusicSpring.db.entities.entity.TrackEntity;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-17T00:39:47+0300",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.jar, environment: Java 1.8.0_382 (Amazon.com Inc.)"
)
public class TrackMapperImpl implements TrackMapper {

    @Override
    public TrackDTO toDto(TrackEntity source) {
        if ( source == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String author = null;
        String text = null;
        String file = null;
        Long album_id = null;

        id = source.getId();
        name = source.getName();
        author = source.getAuthor();
        text = source.getText();
        file = source.getFile();
        album_id = mapAlbumToId( source.getAlbum() );

        TrackDTO trackDTO = new TrackDTO( id, name, author, text, file, album_id );

        return trackDTO;
    }
}
