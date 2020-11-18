package ru.nsu.fit.g18214.yakovlev;

public class Main {
  public static void main(String[] args) {
    Thread thread = new Thread(new Work());
    Work work = new Work();
    thread.start();
    try {
      thread.join();

    } catch (InterruptedException ignored) {
      assert false;
    }
    work.run();
  }
}
