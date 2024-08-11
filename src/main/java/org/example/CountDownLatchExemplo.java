package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CountDownLatchExemplo {

  public static CountDownLatch countDownLatch;

  /*
   Executo a tarefa pricipal 2x, e so entao as tarefas segundaria sao executadas.

   CountDownLatch, nao executa em loop, por isso precisamos reinicia-lo manualmente

   */
  public static void main(String[] args) {
    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
    reiniarAContagem();

    Runnable PrincipalTask = () -> {
      System.out.println("PrincipalTask " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss")));
      countDownLatch.countDown();
    };

    Runnable task2 = () -> {
      esperarTarefaPricipal();
      System.out.println("task2 " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss")));
    };

    Runnable task3 = () -> {
      esperarTarefaPricipal();
      System.out.println("task3 " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss")));
      reiniarAContagem();
    };

    //Executa a tarefa sem delay inicial a cada 1 segundo
    scheduledExecutorService.scheduleAtFixedRate(PrincipalTask, 0, 1, TimeUnit.SECONDS);
    //Execura a tarefa sem delay inicial, porem sempre a cada execucao tem um delay de 3 segundos
    scheduledExecutorService.scheduleWithFixedDelay(task2, 0, 3, TimeUnit.SECONDS);
    scheduledExecutorService.scheduleWithFixedDelay(task3, 0, 3, TimeUnit.SECONDS);
  }

  private static void reiniarAContagem() {
    //Reiniciando a contagem
    countDownLatch = new CountDownLatch(2);
  }

  private static void esperarTarefaPricipal() {
    try {
      countDownLatch.await();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

}
