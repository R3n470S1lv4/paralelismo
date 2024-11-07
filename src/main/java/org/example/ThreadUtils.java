package org.example;

public class ThreadUtils {

  public static void sleep(int segundos)  {
    try {
      Thread.sleep(segundos * 1000L);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

}
