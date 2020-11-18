package ru.nsu.fit.g18214.yakovlev;

public class Main {
  public static void main(String[] args) {
    Founder founder = new Founder(new Company(8));
    founder.start();
  }
}
