import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
  public static void main(String[] args) {
    Thread[] threads = new Thread[5];
    Fork[] forks = new Fork[5];
    for (int i = 0; i < 5; i++) {
      forks[i] = new Fork();
    }

    ReentrantLock lock = new ReentrantLock(true);
    for (int i = 0; i < 5; i++) {
      threads[i] = new Thread(new Phil(lock, i, forks[(i + 1) % 5], forks[i % 5]));
    }
    Arrays.stream(threads).forEach(Thread::start);
  }
}
