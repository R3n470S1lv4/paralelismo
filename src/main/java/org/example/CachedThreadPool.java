package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class CachedThreadPool {

  /*
   newCachedThreadPool, criar quantas threads forem necesarias para executar cada uma das tarefas
   se as threads ficarem ocisosas por 60 segundos elas sao finalizadas automaticamente
   */
  public static void main(String[] args) throws ExecutionException, InterruptedException {
    ExecutorService executorService = Executors.newCachedThreadPool();

    Callable<Integer> callable = () -> {
      ThreadUtils.sleep(1);
      System.out.println("passou!");
      Thread.currentThread().interrupt();
      return 99;
    };

    Collection<Callable<Integer>> tasks = List.of(
        new MyCallable(1, 1),
        new MyCallable(2, 2),
        new MyCallable(4, 3),
        new MyCallable(3, 4),
        new MyCallable(5, 5),
        new MyCallable(6, 6),
        new MyCallable(7, 7),
        new MyCallable(8, 8),
        new MyCallable(9, 9),
        new MyCallable(10, 10)
    );

    List<Future<Integer>> futures = tasks.stream().map(executorService::submit).toList();

//    executorService.shutdown();

    //Espera que todas as tarefas terminem em 11 segundos
    executorService.awaitTermination(11, TimeUnit.SECONDS);

    //Inicia uma nova tarefa, esta deve ser executada em uma thread reciclada, nao deve criar uma nova
    executorService.submit(new MyCallable(11, 10));

    System.out.println("finalizou a main " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss")));
  }
}