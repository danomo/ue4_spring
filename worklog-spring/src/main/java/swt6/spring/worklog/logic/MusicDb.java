package swt6.spring.worklog.logic;

import swt6.spring.worklog.domain.Album;
import swt6.spring.worklog.domain.Genre;
import swt6.spring.worklog.domain.Song;

import java.util.List;

public interface MusicDb {

//========================= SONG ==================================
    Song syncSong(Song song);

    Song findSongById(Long id);

    void deleteSongById(Long id);

    List<Song> findAllSongs();


//========================= ALBUM ==================================
    Album syncAlbum(Album album);

    Album findAlbumById(Long id);

    void deleteAlbumById(Long id);

    List<Album> findAllAlbums();


//========================= GENRE ==================================
    Genre syncGenre(Genre genre);

    Genre findGenreById(Long id);

    void deleteGenreById(long id);

    List<Genre> findAllGenres();

}
