package eu.kotkas.villak.core;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

/**
 * @author Kristen Kotkas
 */
@SpringBootApplication
@Log4j2
public class Main {

  private static String staticFolderPath;
  private static String port;

  public static void main(String[] args) {
    if (args.length == 2) {
      port = args[0];
      staticFolderPath = args[1];
      log.info("Running on port: {}", port);
      log.info("Reading game folder from: {}", staticFolderPath);
    } else {
      log.error("Provide application port and/or data folder absolute path");
      System.exit(-1);
    }
    SpringApplication app = new SpringApplication(Main.class);
    app.setDefaultProperties(Collections.singletonMap("server.port", getPort()));
    app.run(args);
  }

  public static String getDataFileLocation() {
    return staticFolderPath + "/game.json";
  }

  public static String getStaticFolderPath() {
    return staticFolderPath + "/";
  }

  public static String getPort() {
    return port;
  }
}
