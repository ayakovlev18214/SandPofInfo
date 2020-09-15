package ru.nsu.fit.g18214.yakovlev;

class Work implements Runnable {

  public void run()  {
    String[] strings = new String[10];
    for (int i = 0; i<strings.length; i++) {
      strings[i] = Thread.currentThread().toString();
    }
    print(strings);
  }

  private synchronized void print(String[] strings) {
    for (String string : strings) {
      System.out.println(string);
    }
  }
}
