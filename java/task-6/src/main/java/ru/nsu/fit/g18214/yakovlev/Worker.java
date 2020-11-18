package ru.nsu.fit.g18214.yakovlev;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Worker implements Runnable {

  private Department department;
  private CyclicBarrier barrier;

  public Worker(Department department, CyclicBarrier barrier) {
    this.department = department;
    this.barrier = barrier;
  }

  @Override
  public void run() {
    department.performCalculations();
    System.out.println("Department "
      + department.getIdentifier()
      + " finished with "
      + department.getCalculationResult());
    try {
      barrier.await();
    } catch (InterruptedException | BrokenBarrierException ignored) {
      assert false;
    }
  }
}
