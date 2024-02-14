package dev.MusicSpring.mappers;

import dev.MusicSpring.db.dto.ShortTrack;
import dev.MusicSpring.db.entities.entity.TrackEntity;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-12T18:49:18+0300",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.jar, environment: Java 1.8.0_382 (Amazon.com Inc.)"
)
public class ShortTrackMapperImpl implements ShortTrackMapper {

    @Override
    public ShortTrack toDto(TrackEntity track) {
        if ( track == null ) {
            return null;
        }

        ShortTrack shortTrack = new ShortTrack();

        shortTrack.setId( track.getId() );
        shortTrack.setName( track.getName() );
        shortTrack.setAuthor( track.getAuthor() );
        shortTrack.setFile( track.getFile() );

        return shortTrack;
    }
}
