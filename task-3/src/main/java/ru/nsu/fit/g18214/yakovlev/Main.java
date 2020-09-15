package ru.nsu.fit.g18214.yakovlev;

public class Main {
  public static void main(String[] args) {
    Work work = new Work();
    Thread[] threads = new Thread[4];
    for (int i = 0; i < threads.length; i++) {
      threads[i] = new Thread(work);
    }
    for(Thread thread  : threads) {
      thread.start();
    }


  }
}
