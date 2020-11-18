package ru.nsu.fit.g18214.yakovlev;

public class Main {
  public static void main(String[] args) {
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        while (!Thread.currentThread().isInterrupted()) {
          System.out.println("YA JIVOY");
        }
      }
    });
    thread.start();
    try {
      Thread.sleep(2000);
      thread.interrupt();
      thread.join();
    } catch (InterruptedException ignored) {
      assert false;
    }
  }
}
