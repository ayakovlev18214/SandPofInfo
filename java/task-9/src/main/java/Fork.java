import java.util.concurrent.locks.ReentrantLock;

public class Fork {

  private ReentrantLock lock = new ReentrantLock(true);


  public void getFork() {
    lock.lock();
  }

  public void dropFork() {
    lock.unlock();
  }
}
