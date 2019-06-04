package swt6.spring.worklog.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Entity
public class Song implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private Long duration;
    private String interpret;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "songs")
    private Set<Album> albums = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Genre genre;

    public Song(){}

    public Song(String title, long duration, String interpret) {
        this.title = title;
        this.duration = duration;
        this.interpret = interpret;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getInterpret() {
        return interpret;
    }

    public void setInterpret(String interpret) {
        this.interpret = interpret;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public void addAlbum(Album album){
        this.albums.add(album);
        album.getSongs().add(this);
    }

    public Genre getGenre() {
        return genre;
    }

    public String getGenreName() {
        if(genre!=null) {
            return genre.getName();
        }else{
            return "noGenreSet";
        }
    }
    public String getDurationPretty() {
        //https://stackoverflow.com/questions/625433/how-to-convert-milliseconds-to-x-mins-x-seconds-in-java
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(this.duration),
                TimeUnit.MILLISECONDS.toSeconds(this.duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(this.duration))
        );
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
        genre.getSongs().add(this);
    }

    public String toString(){
        return "Title: " + this.title + "(" + this.genre + ") - Duration:" + this.duration + " Interpret:" + this.interpret + " ID:" + this.id;
    }
}

