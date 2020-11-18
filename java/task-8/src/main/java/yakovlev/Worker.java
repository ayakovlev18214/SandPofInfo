package yakovlev;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Worker implements Runnable {

  private long from;
  private long to;
  private double result;
  private CyclicBarrier barrier;

  public Worker(int from, int to, CyclicBarrier barrier) {
    this.from = from;
    this.to = to;
    this.barrier = barrier;
  }

  public void shiftTime(long shift) {
    this.to += shift;
    this.from += shift;
  }

  @Override
  public void run() {
    while (!Thread.interrupted()) {
      for (long i = from; i < to; i++) {
        result += (Math.pow(-1, i) / (2 * i + 1));
      }
      try {
        barrier.await();
      } catch (BrokenBarrierException e) {
        assert false;
      } catch (InterruptedException ignored) {
        return;
      }
    }
  }


  public double getResult() {
    return result;
  }
}
