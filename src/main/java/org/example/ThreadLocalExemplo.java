package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalExemplo {

  /**
   * Podemos usar ThreadLocal para controlar o recurso compartilhado entre as threads
   * <p>
   * Possibilitando que cada thrad trabalhe com um instancia distinta do recurso,
   * assim nao ha concorrencia.
   * <p>
   * Cada thread tem um copia do recurso original e nao compartilhado.
   */


  private static final Counter COUNTER = new Counter();

  public static void main(String[] args) {
    ExecutorService executorService = Executors.newFixedThreadPool(2);

    Runnable counter1 = () -> {
      for (int i = 0; i < 10; i++) {
        COUNTER.inc();
        System.out.println(Thread.currentThread().getName() + " - count " + COUNTER.getCount());
      }
      ThreadUtils.sleep(1);
    };

    Runnable counter2 = () -> {
      for (int i = 0; i < 10; i++) {
        COUNTER.inc();
        System.out.println(Thread.currentThread().getName() + " - count " + COUNTER.getCount());
      }
      ThreadUtils.sleep(1);
    };

    executorService.execute(counter1);
    executorService.execute(counter2);
    executorService.shutdown();
  }
}

class Counter {

  private final ThreadLocal<Integer> count = ThreadLocal.withInitial(() -> 0);

  public void inc(){
    count.set(count.get() + 1);
  }

  public int getCount() {
    return count.get();
  }
}