package eu.kotkas.villak.core.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Kristen Kotkas
 */
public class IdUtil {
  private static final AtomicInteger counter = new AtomicInteger();

  public static int getId() {
    return counter.getAndIncrement();
  }
}
