import java.util.Comparator;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Sorter implements Runnable {
  private List<String> stringList;
  private ReentrantLock lock;

  public Sorter(List<String> stringList, ReentrantLock lock) {
    this.stringList = stringList;
    this.lock = lock;
  }

  @Override
  public void run() {
    while (!Thread.interrupted()) {
      try {
        Thread.sleep(5000);
        lock.lock();
        stringList.sort(Comparator.naturalOrder());
        lock.unlock();
      } catch (InterruptedException e) {
        return;
      }
    }
  }
}
