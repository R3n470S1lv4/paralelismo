package org.example;

import java.util.Objects;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class MyRunnable implements Runnable {

  private final int id;
  private final int delay;
  private CyclicBarrier cyclicBarrier;

  public MyRunnable(int id, int delay) {
    this.id = id;
    this.delay = delay;
  }

  public MyRunnable(int id, int delayInSeconds, CyclicBarrier cyclicBarrier) {
    this.id = id;
    this.delay = delayInSeconds;
    this.cyclicBarrier = Objects.requireNonNull(cyclicBarrier);
  }

  @Override
  public void run() {
      System.out.println("starting task id: " + id);
      ThreadUtils.sleep(delay);
      await();
      System.out.println(Thread.currentThread().getName() + " done id: " + id);
  }

  private void await(){
    if (Objects.nonNull(cyclicBarrier)) {
      int waitingFor = cyclicBarrier.getParties() - cyclicBarrier.getNumberWaiting();
      System.out.println("waiting for: " + waitingFor + " thread remaining");
      try {
        cyclicBarrier.await();
      } catch (InterruptedException | BrokenBarrierException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
