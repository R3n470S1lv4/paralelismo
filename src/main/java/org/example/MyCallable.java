package org.example;

import java.util.Objects;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;

public class MyCallable implements Callable<Integer> {

  private final int id;
  private final int delay;
  private CyclicBarrier cyclicBarrier;

  public MyCallable(int id, int delay) {
    this.id = id;
    this.delay = delay;
  }

  public MyCallable(int id, int delayInSeconds, CyclicBarrier cyclicBarrier) {
    this.id = id;
    this.delay = delayInSeconds;
    this.cyclicBarrier = Objects.requireNonNull(cyclicBarrier);
  }

  @Override
  public Integer call() throws Exception {
    ThreadUtils.sleep(delay);
    await();
    System.out.println(Thread.currentThread().getName() + " done id: " + id);
    return id;
  }

  private void await() throws InterruptedException, BrokenBarrierException {
    if (Objects.nonNull(cyclicBarrier)) {
      int waitingFor = cyclicBarrier.getParties() - cyclicBarrier.getNumberWaiting();
      System.out.println("waiting for: " + waitingFor + " thread remaining");
      cyclicBarrier.await();
    }
  }
}
