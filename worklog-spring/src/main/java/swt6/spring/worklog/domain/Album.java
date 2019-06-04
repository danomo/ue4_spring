package swt6.spring.worklog.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Album implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    private String title;

    @Column(name = "release_year")
    private int year;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Song> songs = new HashSet<>();

    public Album(){}

    public Album(String title, int year) {
        this.title = title;
        this.year = year;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Set<Song> getSongs() {
        return songs;
    }

    public void setSongs(Set<Song> songs) {
        this.songs = songs;
    }

    public void addSong(Song song){
        this.songs.add(song);
        song.getAlbums().add(this);
    }

    public String toString(){
        return "Album: " + this.title + " - Year: " + this.year + " ID: " + this.id;
    }
}
