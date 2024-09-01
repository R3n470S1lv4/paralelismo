package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExemplo {

  private static int count = 0;

  /*
   ReentrantLock, substituto para o synchronized, eh mais flexivel pois
   podemos bloquear/desbloquear diversos pontos do codigo

   Usado para controlar o acesso a recursos, evitando concorrencia entre threads.

   Aplicacao: temos o recurso/variavel `count` que tem o valor incrementado a cada execucao da thread,
   quando o incremento acontece em paralelo (mais de uma thread), o valor de `count` pode ficar errado,
   pois temos mais de uma thread tentando realizar o incremento ao mesmo tempo.

   Ao usar lock evitamos esse efeito erratido, pois somente uma thread podera incrementar o valor e,
   somente apos liberar o lock as demais threads poderam realizar o incremento.

   */
  public static void main(String[] args) {
    ExecutorService executorService = Executors.newCachedThreadPool();
    Lock reentrantLock = new ReentrantLock();

    Runnable runnable = () -> {
      reentrantLock.lock();
      count++;
      System.out.println(Thread.currentThread().getName() + " count: " + count);
      reentrantLock.unlock();
    };

    for (int i = 0; i < 6; i++) {
      executorService.execute(runnable);
    }

    executorService.shutdown();
  }
}
