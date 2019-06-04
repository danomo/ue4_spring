package swt6.spring.worklog.test;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.spring.worklog.domain.*;
import swt6.spring.worklog.logic.MusicDb;

import javax.persistence.EntityManagerFactory;

import static swt6.util.PrintUtil.printSeparator;
import static swt6.util.PrintUtil.printTitle;

@SuppressWarnings("Duplicates")
public class LogicTestMusicDb {

  private static  Album alb1;
  private static  Album alb2;
  private static  Album alb3;

  private static Song song1;
  private static Song song2;
  private static Song song3;
  private static Song song4;
  private static Song song5;

  private static Genre genre1;
  private static Genre genre2;
  private static Genre genre3;

  private static void testSaveAlbum(MusicDb musicDb){
    alb1 = new Album("Album 1", 2018);
    alb2 = new Album("Album 2", 2020);
    alb3 = new Album("Album 3", 2015);

    alb1 = musicDb.syncAlbum(alb1);
    alb2 = musicDb.syncAlbum(alb2);
    alb3 = musicDb.syncAlbum(alb3);
  }

  private static void testSaveSong(MusicDb musicDb){
    song1 = new Song("Song1", 20000, "Singer1");
    song2 = new Song("Song2", 20000, "Singer2");
    song3 = new Song("Song3", 20000, "Singer3");
    song4 = new Song("Song4", 20000, "Singer4");
    song5 = new Song("Song5", 20000, "Singer5");

    song1 = musicDb.syncSong(song1);
    song2 = musicDb.syncSong(song2);
    song3 = musicDb.syncSong(song3);
    song4 = musicDb.syncSong(song4);
    song5 = musicDb.syncSong(song5);
  }

  private static void testSaveGenre(MusicDb musicDb){
    genre1 = new Genre("Genre1");
    genre2 = new Genre("Genre2");
    genre3 = new Genre("Genre3");

    genre1 = musicDb.syncGenre(genre1);
    genre2 = musicDb.syncGenre(genre2);
    genre3 = musicDb.syncGenre(genre3);
  }

  private static void testSetGenreInSong(MusicDb musicDb){
    song1.setGenre(genre1);
    song2.setGenre(genre2);
    song3.setGenre(genre2);
    song4.setGenre(genre3);
    song5.setGenre(genre1);

    song1 = musicDb.syncSong(song1);
    song2 = musicDb.syncSong(song2);
    song3 = musicDb.syncSong(song3);
    song4 = musicDb.syncSong(song4);
    song5 = musicDb.syncSong(song5);
  }

  private static void testSetAlbumInSong(MusicDb musicDb){
    song1.addAlbum(alb1);
    song2.addAlbum(alb2);
    song3.addAlbum(alb3);

    song1 = musicDb.syncSong(song1);
    song2 = musicDb.syncSong(song2);
    song3 = musicDb.syncSong(song3);

    alb1 = musicDb.findAlbumById(alb1.getId());
    song4.addAlbum(alb1);

    alb2 = musicDb.findAlbumById(alb2.getId());
    song5.addAlbum(alb2);

    song4 = musicDb.syncSong(song4);
    song5 = musicDb.syncSong(song5);
  }

  private static void testGetAllSongs(MusicDb musicDb) {
    for (Song s : musicDb.findAllSongs()) {
      System.out.println(s.toString());
      s.getAlbums().forEach(album ->
              System.out.println("   " + album.toString()));
    }
  }

  private static void testGetAllGenres(MusicDb musicDb) {
    for (Genre g : musicDb.findAllGenres()) {
      System.out.println(g.toString());
      g.getSongs().forEach(song ->
              System.out.println("   " + song.toString()));
    }
  }

  private static void testGetAllAlbums(MusicDb musicDb) {
    for (Album a : musicDb.findAllAlbums()) {
      System.out.println(a.toString());
      a.getSongs().forEach(song ->
              System.out.println("   " + song.toString()));
    }
  }


  private static void testBusinessLogicWithSpringDataRepositories() {
    try (AbstractApplicationContext appCtx =
                 new ClassPathXmlApplicationContext(
                         "swt6/spring/worklog/test/applicationContext-jpa2.xml")) {

      EntityManagerFactory emFactory = appCtx.getBean(EntityManagerFactory.class);
      final MusicDb musicDb = appCtx.getBean("musicDb", MusicDb.class);

      printTitle("testSaveAlbum", 60, '-');
      testSaveAlbum(musicDb);

      printTitle("testSaveGenre", 60, '-');
      testSaveGenre(musicDb);

      printTitle("testSaveSong", 60, '-');
      testSaveSong(musicDb);

      printTitle("testSetGenreInSong", 60, '-');
      testSetGenreInSong(musicDb);

      printTitle("testSetAlbumInSong", 60, '-');
      testSetAlbumInSong(musicDb);

      printTitle("testGetAllSongs", 60, '-');
      testGetAllSongs(musicDb);

      printTitle("testGetAllGenres", 60, '-');
      testGetAllGenres(musicDb);

      printTitle("testGetAllAlbums", 60, '-');
      testGetAllAlbums(musicDb);
    }

  }

  public static void createTestData(MusicDb musicDb){
      printTitle("testSaveAlbum", 60, '-');
      testSaveAlbum(musicDb);

      printTitle("testSaveGenre", 60, '-');
      testSaveGenre(musicDb);

      printTitle("testSaveSong", 60, '-');
      testSaveSong(musicDb);

      printTitle("testSetGenreInSong", 60, '-');
      testSetGenreInSong(musicDb);

      printTitle("testSetAlbumInSong", 60, '-');
      testSetAlbumInSong(musicDb);
  }

  // run with 
  // --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.lang.invoke=ALL-UNNAMED
  public static void main(String[] args) {

  	printSeparator(60);
  	printTitle("testBusinessLogicWithSpringDataRepositories", 60); 
  	printSeparator(60);
    testBusinessLogicWithSpringDataRepositories();
  }
}
