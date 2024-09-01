package org.example;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockExemplo {

  private static int count = 1;

  /*
   ReentrantReadWriteLock, substituto para o synchronized, eh mais flexivel pois
   podemos bloquear/desbloquear diversos pontos do codigo

   Usado para controlar o acesso a recursos, evitando concorrencia entre threads, com esta implemtancao podemos
   controlar a escrita/leitura do recurso

   Aplicao: temos o recurso/variavel `count` que tem o valor incrementado a cada execucao da thread,
   quando o incremento acontece em paralelo (mais de uma thread), o valor de `count` pode ficar errado,
   pois temos mais de uma thread tentando realizar o incremento ao mesmo tempo.

   Ao usar lock evitamos esse efeito erratido, pois somente uma thread podera incrementar o valor e,
   somente apos liberar o lock as demais threads poderam realizar o incremento.

   Neste exemplo estamos controlando a leitura e escrita do recurso, a escrita so ira acontecer
   quando todos os locks forem liberados tanto o de leitura quanto o de escrita, Se um dos locks nao
   for liberado nenhuma outra thread podera ser executada.

   Usando somente lock de leitura, nao bloqueia a leitura das demais threads, ou seja posso ter varias threads lendo o mesmo recuros sem ser bloqueada,
   porem Lock de escrita bloqueia a escrita das demais threads, sendo assim somente uma thread por vez pode modificar o recurso.

   Exemplo:
     1. Combinando escrita e leitura
       1.1. Quando uma thread esta escrevendo nenhuma outra pode escrever ou ler
       1.2. Quando uma thread esta lendo nenhuma outra pode escrever ou ler
     2. Somente escrita, quando uma thread esta escrevendo nenhuma outra pode escrever
     3. Somente leitura, nao bloqueio varias threads podem ler o mesmo recurso
   */
  public static void main(String[] args) {
    ExecutorService executorService = Executors.newCachedThreadPool();
    ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    Runnable task1 = () -> {
      String name = Thread.currentThread().getName();
      Lock writeLock = readWriteLock.writeLock();
      writeLock.lock();
      System.out.println(name + " Escrevendo, valor atual: " + count);
      count++;
      System.out.println(name + " Escrito: " + count);
      writeLock.unlock();
    };
    Runnable task2 = () -> {
      String name = Thread.currentThread().getName();
      Lock readLock = readWriteLock.readLock();
      readLock.lock();
      System.out.println(name + " Lendo Valor atual: " + count);
      System.out.println(name + " Lido: " + count);
      readLock.unlock();
    };

    for (int i = 0; i < 6; i++) {
      executorService.execute(task1);
      executorService.execute(task2);
    }

    executorService.shutdown();
  }
}
