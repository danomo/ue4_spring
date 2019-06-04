package swt6.spring.worklog.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import swt6.spring.worklog.logic.MusicDb;
import swt6.spring.worklog.test.LogicTestMusicDb;

@Component
//@Profile("dev")
public class DatabaseInitializer implements CommandLineRunner {

  @Autowired
  private MusicDb musicDb;

  @Override
  public void run(String... args) throws Exception {
    LogicTestMusicDb.createTestData(musicDb);
  }
}