package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueExemplo {

  /*
  SynchronousQueue, usada para torcar mensagens entre threads/terefas

  O metodo put(E) adiciona um elemento na fila e aguarda/bloequeia ate que o elemento seja removido.
  Para remover o elemente usamos o metodo take em outra thread/tarefa, dessa forma a thread bloqueada
  sera libera.

  Portando a fila tera somente um elemento por vez.
  Exemplos:
    1. Thread 1 adiciona elemento na fila, e fica bloeqada a ate que a thread 2 remova o elemento
    2. Nenhum elemento eh adicionado na fila, thread 2 fica bloqueada ate que algum elemento seja adiconado


  Uso: Posso realziar o processamento em um thread e ao final colocar o reseltado na fila, esta
  thread ficara bloqueada ate que outra thread pegue o resulda da fila.
   */
  public static void main(String[] args) {
    ExecutorService executorService = Executors.newCachedThreadPool();
    SynchronousQueue<String> synchronousQueue = new SynchronousQueue<>();

    Runnable task1 = () -> {
      Thread.currentThread().setName("task 1");
      putInQueue(synchronousQueue);
      System.out.println(Thread.currentThread().getName() + " - Executando tarefa...");
    };

    Runnable task2 = () -> {
      Thread.currentThread().setName("task 2");
      takeFromQueue(synchronousQueue);
      System.out.println(Thread.currentThread().getName() + " - Tarefa executada...");
    };

    executorService.execute(task1);
    executorService.execute(task2);
    executorService.shutdown();
  }

  private static void takeFromQueue(SynchronousQueue<String> synchronousQueue){
    try {
      System.out.println(Thread.currentThread().getName() + " - Removendo da fila");
      synchronousQueue.take();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException(e);
    }
  }

  private static void putInQueue(SynchronousQueue<String> synchronousQueue) {
    try {
      System.out.println(Thread.currentThread().getName() + " - Colocando na fila");
      synchronousQueue.put("elemento na fila");
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException(e);
    }
  }


}
