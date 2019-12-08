package eu.kotkas.villak.core;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Kristen Kotkas
 */
@SpringBootApplication
@Log4j2
public class Main {

  private static String dataLocationPath;

  public static String getDataLocationPath() {
    return dataLocationPath;
  }

  public static void main(String[] args) {
    if (args.length == 1) {
      Main.dataLocationPath = args[0];
    } else {
      log.error("Provide data file location");
      System.exit(-1);
    }

    SpringApplication.run(Main.class, args);
  }
}
