package yakovlev;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import sun.misc.Signal;
import sun.misc.SignalHandler;

class Logic {
  private final int itersCount = 10000000;
  private List<Worker> workers = new ArrayList<>();
  private double result = 0;
  private boolean flag = false;
  private int threadsCount;
  private CyclicBarrier barrier;

  Logic(int threadsNum) {
    threadsCount = threadsNum;
  }

  private void recountValues() {
    for (Worker worker : workers) {
      worker.shiftTime(itersCount);
    }
    barrier.reset();
  }

  private void setFlag() {
    flag = true;
  }

  double run() {
    SignalHandler signalHandler = sig -> setFlag();
    Signal.handle(new Signal("INT"), signalHandler);
    barrier= new CyclicBarrier(threadsCount + 1, this::recountValues);

    Thread[] threads = new Thread[threadsCount];

    for (int i = 0; i < threadsCount; i++) {
      Worker worker = new Worker(
        itersCount * (i / threadsCount),
        itersCount * ((i + 1) / threadsCount),
        barrier);

      workers.add(worker);
      threads[i] = new Thread(worker);
    }

    int k = 0;
    long time = System.currentTimeMillis();

    for (Thread thread : threads) {
      thread.start();
    }

    while (!flag) {
      try {
        barrier.await();
      } catch (InterruptedException | BrokenBarrierException e) {
        assert false;
      }
    }

    for (Thread thread : threads) {
      thread.interrupt();
      try {
        thread.join();
      } catch (InterruptedException e) {
        assert false;
      }
    }
    for (Worker worker : workers) {
      result += worker.getResult();
    }

    System.out.println("TIME = " + (System.currentTimeMillis() - time) / 1000);
    System.out.println(k);
    return result * 4;
  }

}
