package org.example;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FixedThreadPool {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    ExecutorService executorService = Executors.newFixedThreadPool(5);

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
        callable,
        new MyCallable(10, 1),
        new MyCallable(11, 2)
    );

    List<Future<Integer>> futures = tasks.stream().map(executorService::submit).toList();

    executorService.shutdown();
    for (Future<Integer> future : futures) {
      future.get();
    }

  }
}

