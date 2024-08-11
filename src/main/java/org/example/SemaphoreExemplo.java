package org.example;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreExemplo {

  public static final Semaphore SEMAPHORE = new Semaphore(2);

  /*
    new Semaphore(2), Um semaforo que permite a execucao de 2 tarefas, as demais ficam em espera
    ate que o semaforo seja liberado.

    SEMAPHORE.acquire(), Adquire a passagem livre pelo semafore, se nao conseguir fica em espera ate
                         que seja libera um passagem.
    SEMAPHORE.release(), Libera a passagem pelo semaforo.
   */
  public static void main(String[] args) {
    ExecutorService executorService = Executors.newCachedThreadPool();

    Runnable task1 = () -> {
      acquire();
      System.out.println("starting task 1");
      ThreadUtils.sleep(5);
      System.out.println("task 1 done.");
    };
    Runnable task2 = () -> {
      acquire();
      System.out.println("starting task 2");
      ThreadUtils.sleep(10);
      // Libera/Abre o semaforo para que as threads bloqueadas possam seguir com o processameto
      SEMAPHORE.release();
      System.out.println("task 2 done.");
    };
    Runnable task3 = () -> {
      // Fica bloqueado em espera ate que o semaforo seja liberado, neste caso a task 2 faz a liberacao
      acquire();
      System.out.println("starting task 3");
      ThreadUtils.sleep(5);
      System.out.println("task 3 done.");
    };

    List<Runnable> tasks = List.of(task1, task2, task3);
    tasks.forEach(executorService::execute);

    executorService.shutdown();
  }


  private static void acquire() {
    try {
      SEMAPHORE.acquire();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
