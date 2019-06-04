package swt6.spring.worklog.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import swt6.spring.worklog.repo.AlbumRepository;
import swt6.spring.worklog.repo.GenreRepository;
import swt6.spring.worklog.repo.SongRepository;
import swt6.spring.worklog.domain.Album;
import swt6.spring.worklog.domain.Genre;
import swt6.spring.worklog.domain.Song;

import java.util.List;

@Component("musicDb")
@Transactional
@Primary
public class MusicImpl implements MusicDb {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private GenreRepository genreRepository;

    public void setAlbumRepository(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public void setSongRepository(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public void setGenreRepository(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public Song syncSong(Song song) {
        return songRepository.saveAndFlush(song);
    }

    @Override
    public Song findSongById(Long id) {
        return songRepository.findById(id).get();
    }

    @Override
    public void deleteSongById(Long id) {
        songRepository.deleteById(id);
    }

    @Override
    public List<Song> findAllSongs() {
        return songRepository.findAll();
    }

    @Override
    public Album syncAlbum(Album album) {
        return albumRepository.saveAndFlush(album);
    }

    @Override
    public Album findAlbumById(Long id) {
        return albumRepository.findById(id).get();
    }

    @Override
    public void deleteAlbumById(Long id) {
        albumRepository.deleteById(id);
    }

    @Override
    public List<Album> findAllAlbums() {
        return albumRepository.findAll();
    }

    @Override
    public Genre syncGenre(Genre genre) {
       return genreRepository.saveAndFlush(genre);
    }

    @Override
    public Genre findGenreById(Long id) {
        return genreRepository.findById(id).get();
    }

    @Override
    public void deleteGenreById(long id) {
        genreRepository.deleteById(id);
    }

    @Override
    public List<Genre> findAllGenres() {
        return genreRepository.findAll();
    }
}
