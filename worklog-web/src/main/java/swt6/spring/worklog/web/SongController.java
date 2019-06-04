package swt6.spring.worklog.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import swt6.spring.worklog.domain.*;
import swt6.spring.worklog.logic.MusicDb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class SongController {

    private final Logger logger = LoggerFactory.getLogger(SongController.class);

    @Autowired
    private MusicDb musicDb;

    @RequestMapping("/albums/{albumId}/songs")
    public String list(@PathVariable("albumId") long albumId, Model model) {
        Album album =  musicDb.findAlbumById(albumId);

        logger.debug("details view for album {}", album);

        List<Song> songs = new ArrayList<>(album.getSongs());
        Collections.sort(songs, (e1, e2) -> e1.getTitle().compareTo(e2.getTitle()) );

        model.addAttribute("album", album);
        model.addAttribute("songs", songs);

        return "songList";
    }

    @RequestMapping(value="/albums/{albumId}/song/new", method = RequestMethod.GET)
    public String initNew(@PathVariable("albumId") Long albumId, Model model) {

        Song song = new Song();

        Album album = this.musicDb.findAlbumById(albumId);
        List<Genre> genres = new ArrayList<>(musicDb.findAllGenres());
        Collections.sort(genres, (g1, g2) -> g1.getName().compareTo(g2.getName()) );

        song.addAlbum(album);

        model.addAttribute("song", song);
        model.addAttribute("genres",genres);
        logger.debug("song template [}", song);

        return "song";
    }

    @RequestMapping(value="/albums/{albumId}/song/new", method = RequestMethod.POST)
    public String processNew(@PathVariable("albumId") Long albumId,
                             @ModelAttribute("song") Song song,
                             BindingResult result, Model model) {

        return internalProcessUpdate(albumId, song, result);
    }

    private String internalProcessUpdate(Long albumId, Song song, BindingResult result) {
        if (result.hasErrors()) {
            return "song";
        }
        else {
            song.addAlbum(musicDb.findAlbumById(albumId));
            song = musicDb.syncSong(song);

            logger.debug("added/updated song {}", song);
            return "redirect:/albums/{albumId}/songs";
        }
    }


    @RequestMapping(value="/albums/{albumId}/songs/{songId}/update", method = RequestMethod.GET)
    public String initUpdate(@PathVariable("albumId") Long albumId,
                             @PathVariable("songId") Long songId,
                             Model model) {

        Song song = musicDb.findSongById(songId);
        List<Genre> genres = new ArrayList<>(musicDb.findAllGenres());
        Collections.sort(genres, (g1, g2) -> g1.getName().compareTo(g2.getName()) );
        model.addAttribute("song", song);
        model.addAttribute("genres", genres);

        logger.debug("song template [}", song);

        return "song";
    }

    @RequestMapping(value="/albums/{albumId}/songs/{songId}/update", method = RequestMethod.POST)
    public String processUpdate(@PathVariable("albumId") Long albumId,
                                @PathVariable("songId") Long songId,
                                @ModelAttribute("song") Song song,
                                BindingResult result) {
        return internalProcessUpdateWithId(albumId,songId, song, result);
    }

    private String internalProcessUpdateWithId(long albumId, long songId, Song song,BindingResult result) {
        if (result.hasErrors()) {
            return "song";
        }
        else {
            Song oldSong = musicDb.findSongById(songId);
            oldSong.setTitle(song.getTitle());
            oldSong.setDuration(song.getDuration());
            oldSong.setInterpret(song.getInterpret());
            oldSong.setGenre(song.getGenre());
            oldSong = musicDb.syncSong(oldSong);

            logger.debug("updated song {}", oldSong);
            return "redirect:/albums/"+albumId+"/songs/";
        }
    }

}
