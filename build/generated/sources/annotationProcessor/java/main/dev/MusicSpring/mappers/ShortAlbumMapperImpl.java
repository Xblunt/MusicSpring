package dev.MusicSpring.mappers;

import dev.MusicSpring.db.dto.ShortAlbum;
import dev.MusicSpring.db.entities.entity.AlbumEntity;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-22T21:42:37+0300",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.jar, environment: Java 1.8.0_382 (Amazon.com Inc.)"
)
public class ShortAlbumMapperImpl implements ShortAlbumMapper {

    @Override
    public ShortAlbum toDto(AlbumEntity album) {
        if ( album == null ) {
            return null;
        }

        ShortAlbum shortAlbum = new ShortAlbum();

        shortAlbum.setId( album.getId() );
        shortAlbum.setName_album( album.getName_album() );
        shortAlbum.setPicture( album.getPicture() );

        return shortAlbum;
    }
}
