package ru.nsu.fit.g18214.yakovlev;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

public final class Founder {

  private final List<Worker> workers;
  private CyclicBarrier barrier;
  private Company company;

  public Founder(final Company company) {
    barrier = new CyclicBarrier(company.getDepartmentsCount(), company::showCollaborativeResult);
    this.workers = new ArrayList<>(company.getDepartmentsCount());
    for (int i = 0; i < company.getDepartmentsCount(); i++) {
      workers.add(new Worker(company.getFreeDepartment(i), barrier));
    }
    this.company = company;
  }

  public void start() {
    for (final Runnable worker : workers) {
      new Thread(worker).start();
    }
  }
}
