package org.example;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExchangerExemplo {

  public static final Exchanger<String> EXCHANGER = new Exchanger<>();

  /*
    Exchanger, faz a troca multua de mensagem entre 2 threads

    Exemplo:
      1. Thread 1 inicia com o valor "1" e faz a troca com a thread 2 que tem o valor "2",
      apos a troca o valor atual na thread 1 eh "2" e, o valor atual na thread 2 eh "1"

      Para fazer a troca de valores usamos o metod exchange que retorna o tipo paramentrizado.

      Caso nao tenha valor para ser trocado, o valor original se mantem, ou seja, se uma das threads
      nao quiser fazer uma troca nada eh trocado

   */
  public static void main(String[] args) {
    ExecutorService executorService = Executors.newCachedThreadPool();

    Runnable task1 = () -> {
      Thread.currentThread().setName("task 1");
      String threadName = Thread.currentThread().getName();

      String message = "Valor 1";
      System.out.println(threadName + " Valor atual " + message);
      String result = exchange(message);
      System.out.println(threadName + " - Valor apos a troca " + result);
    };

    Runnable task2 = () -> {
      Thread.currentThread().setName("task 2");
      String threadName = Thread.currentThread().getName();
      String message = "valor 2";
      System.out.println(threadName + " Valor atual " + message);
      String result = exchange(message);
      System.out.println(Thread.currentThread().getName() + " - Valor apos a troca " + result);
    };

    executorService.execute(task1);
    executorService.execute(task2);
    executorService.shutdown();
  }

  private static String exchange(String message){
    try {
      return EXCHANGER.exchange(message);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException(e);
    }
  }


}
