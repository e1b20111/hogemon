package oit.is.team2.hogemon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class HogemonApplication {

  public static void main(String[] args) {
    SpringApplication.run(HogemonApplication.class, args);
  }

}
