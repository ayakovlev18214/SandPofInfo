package ru.nsu.fit.g18214.yakovlev;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class Main {

  private static List<Worker> workers = new ArrayList<>();

  private static void printResult() {
    double result = 0;
    for (Worker worker : workers) {
      result += worker.getResult();
    }
    System.out.println(result*4);
  }

  public static void main(String[] args) {
    final int itersCount = 100000000;
    if (args.length != 1) {
      System.out.println("Enter a number of threads");
      return;
    }
    int threadsCount = Integer.valueOf(args[0]);

    CyclicBarrier barrier = new CyclicBarrier(threadsCount, Main::printResult);
    for (int i = 0; i < threadsCount; i++) {
      workers.add(new Worker(
        itersCount * (i / threadsCount),
        itersCount * ((i + 1) / threadsCount),
        barrier));
    }

    for (Worker worker : workers) {
      new Thread(worker).start();
    }
  }
}
