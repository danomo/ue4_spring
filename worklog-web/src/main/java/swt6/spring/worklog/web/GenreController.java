package swt6.spring.worklog.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import swt6.spring.worklog.domain.Album;
import swt6.spring.worklog.domain.Genre;
import swt6.spring.worklog.logic.MusicDb;

import java.util.List;

@Controller
public class GenreController {

    private Logger logger = LoggerFactory.getLogger(GenreController.class);

    @Autowired
    private MusicDb musicDb;

    @RequestMapping("/genres")
    public String list(Model model) {
        List<Genre> genres = this.musicDb.findAllGenres();
        model.addAttribute("genres", genres);
        logger.debug("genres: " + genres);
        return "genreList";
    }

    @RequestMapping(value="/genres/new", method = RequestMethod.GET)
    public String initNew(Model model) {
        Genre genre = new Genre();
        model.addAttribute("genre", genre);
        logger.debug("album template [}", genre);
        return "genre";
    }

    @RequestMapping(value="/genres/new", method = RequestMethod.POST)
    public String processNew(@ModelAttribute("genre") Genre genre,BindingResult result, Model model) {
        return internalProcessUpdate(genre,result);
    }

    private String internalProcessUpdate(Genre genre,BindingResult result) {
        if (result.hasErrors()) {
            return "genre";
        }
        else {
            genre = musicDb.syncGenre(genre);

            logger.debug("added genre {}", genre);
            return "redirect:/genres";
        }
    }

    private String internalProcessUpdateWithId(long genreId, Genre genre,BindingResult result) {
        if (result.hasErrors()) {
            return "genre";
        }
        else {
            Genre oldGenre = musicDb.findGenreById(genreId);
            oldGenre.setName(genre.getName());
            oldGenre = musicDb.syncGenre(oldGenre);

            logger.debug("updated genre {}", oldGenre);
            return "redirect:/genres";
        }
    }

    @RequestMapping(value="/genres/{genreId}/update", method = RequestMethod.GET)
    public String initUpdate(@PathVariable("genreId") Long genreId,
                             Model model) {

        Genre genre = musicDb.findGenreById(genreId);
        model.addAttribute("genre", genre);

        logger.debug("genre template [}", genre);

        return "genre";
    }

    @RequestMapping(value="/genres/{genreId}/update", method = RequestMethod.POST)
    public String processUpdate(@PathVariable("genreId") Long genreId,
                                @ModelAttribute("genre") Genre genre,
                                BindingResult result) {
        return internalProcessUpdateWithId(genreId, genre, result);
    }





}
