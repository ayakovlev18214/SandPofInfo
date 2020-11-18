
import java.util.concurrent.locks.ReentrantLock;

public class Main {
  public static void main(String[] args) {

    ReentrantLock lock1 = new ReentrantLock(true);
    ReentrantLock lock2 = new ReentrantLock(true);

    Thread thread = new Thread(() -> {
      for (int i = 0; i < 10; i++) {
        lock1.lock();
        lock2.lock();
        System.out.println("Child " + i);
        lock1.unlock();
        lock2.unlock();
      }
    });

    lock1.lock();
    thread.start();
    for (int i = 0; i < 10; i++) {
      lock2.lock();
      lock1.unlock();
      System.out.println("Parent " + i);
      lock2.unlock();
      lock1.lock();
    }
  }

}
