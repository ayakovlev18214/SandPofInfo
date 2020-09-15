package ru.nsu.fit.g18214.yakovlev;

class Work implements Runnable {
  @Override
  public void run() {
    for (int i = 0; i < 10; i++) {
      System.out.println("HELLO, THAT'S ME " + Thread.currentThread());
    }
  }
}
