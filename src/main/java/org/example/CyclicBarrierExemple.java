package org.example;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierExemple {

  /*
    Aguarda ate que todas as 5 threads cheguem a barreira, e quando a ultima thread chega a barreira
    dispara uma tarefa de callback (terminado), e so entao liberada as threads para continuar o processamento.
    Podemos usar para sincronizar os processos.

    CyclicBarrier, fica em loop, sempre que a ultima barreira eh atingida a contagem reinicia.

    Para que funcione em ciclo/loop, nao podemos desligar o executor de threads (scheduledExecutorService.shutdown())

   */
  public static void main(String[] args) throws ExecutionException, InterruptedException {
    Runnable terminado = () -> {
      System.out.println("todas as threads foram sincronizadas.");
    };

    CyclicBarrier cyclicBarrier = new CyclicBarrier(5, terminado);
    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

    List<MyRunnable> tasks = List.of(
        new MyRunnable(1, 1, cyclicBarrier),
        new MyRunnable(2, 2, cyclicBarrier),
        new MyRunnable(4, 3, cyclicBarrier),
        new MyRunnable(3, 4, cyclicBarrier),
        new MyRunnable(5, 5, cyclicBarrier)
    );

    tasks.forEach(task -> scheduledExecutorService.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS));
  }
}